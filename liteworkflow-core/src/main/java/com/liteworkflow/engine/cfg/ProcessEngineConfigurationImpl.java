package com.liteworkflow.engine.cfg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.cache.memory.MemoryCacheManager;
import com.liteworkflow.engine.impl.CommandExecutor;
import com.liteworkflow.engine.impl.CommandExecutorImpl;
import com.liteworkflow.engine.impl.HistoryServiceImpl;
import com.liteworkflow.engine.impl.ProcessEngineImpl;
import com.liteworkflow.engine.impl.ProcessInstanceServiceImpl;
import com.liteworkflow.engine.impl.RepositoryServiceImpl;
import com.liteworkflow.engine.impl.RuntimeServiceImpl;
import com.liteworkflow.engine.impl.TaskServiceImpl;
import com.liteworkflow.engine.persistence.mapper.HistoricProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.HistoricTaskMapper;
import com.liteworkflow.engine.persistence.mapper.ProcessDefinitionMapper;
import com.liteworkflow.engine.persistence.mapper.ProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.TaskMapper;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.persistence.service.impl.HistoricProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoricTaskEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.ProcessDefinitionEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.ProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.TaskEntityServiceImpl;

/**
 * 只允许应用程序存在一个Configuration实例
 * 初始化服务上下文，查找流程引擎实现类并初始化依赖的服务
 * 
 * @version
 */
public class ProcessEngineConfigurationImpl implements ProcessEngineConfiguration, ApplicationContextAware
{
	private static final Logger LOG = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);

	/**
	 * Spring上下文
	 */
	private ApplicationContext applicationContext;

	/**
	 * CacheManager
	 */
	private CacheManager cacheManager = new MemoryCacheManager();

	/**
	 * SqlSessionFactory
	 */
	private SqlSessionFactory sqlSessionFactory;

	/**
	 * RepositoryService
	 */
	private RepositoryService repositoryService;

	/**
	 * ProcessInstanceService
	 */
	private ProcessInstanceService processInstanceService;

	/**
	 * TaskService
	 */
	private TaskService taskService;

	/**
	 * HistoryService
	 */
	private HistoryService historyService;

	/**
	 * RuntimeService
	 */
	private RuntimeService runtimeService;

	/**
	 * ProcessEngine
	 */
	private ProcessEngine processEngine;

	/**
	 * ProcessDefinitionEntityService
	 */
	private ProcessDefinitionEntityService processDefinitionEntityService;

	/**
	 * HistoricTaskEntityService
	 */
	private HistoricTaskEntityService historicTaskEntityService;

	/**
	 * TaskEntityService
	 */
	private TaskEntityService taskEntityService;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * ProcessInstanceEntityService
	 */
	private ProcessInstanceEntityService processInstanceEntityService;

	/**
	 * CommandExecutor
	 */
	private CommandExecutor commandExecutor = new CommandExecutorImpl(this);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessEngine buildProcessEngine() throws Exception
	{
		LOG.info("Start to build ProcessEngine.");

		initPersistenceServices();

		initOpenServices();

		this.processEngine = new ProcessEngineImpl(this);

		LOG.info("Build ProcessEngine successfully.");

		return this.processEngine;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ApplicationContext getApplicationContext()
	{
		return this.applicationContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CacheManager getCacheManager()
	{
		return this.cacheManager;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RepositoryService getRepositoryService()
	{
		return this.repositoryService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessInstanceService getProcessInstanceService()
	{
		return processInstanceService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TaskService getTaskService()
	{
		return this.taskService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public HistoryService getHistoryService()
	{
		return this.historyService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RuntimeService getRuntimeService()
	{
		return runtimeService;
	}

	/**
	 * 初始化持久层服务
	 * 
	 * @throws Exception
	 */
	private void initPersistenceServices() throws Exception
	{
		this.processDefinitionEntityService = initProcessDefinitionEntityService(sqlSessionFactory);

		this.historicTaskEntityService = initHistoricTaskEntityService(sqlSessionFactory);
		this.taskEntityService = initTaskEntityService(sqlSessionFactory);

		this.historicProcessInstanceEntityService = initHistoricProcessInstanceEntityService(sqlSessionFactory);
		this.processInstanceEntityService = initProcessInstanceEntityService(sqlSessionFactory, historicProcessInstanceEntityService);
	}

	/**
	 * 初始化开放服务
	 * 
	 */
	private void initOpenServices()
	{
		RepositoryServiceImpl repositoryService = new RepositoryServiceImpl(this, cacheManager, processDefinitionEntityService);
		repositoryService.setCommandExecutor(commandExecutor);
		this.repositoryService = repositoryService;

		HistoryServiceImpl historyService = new HistoryServiceImpl(historicTaskEntityService, historicProcessInstanceEntityService);
		this.historyService = historyService;

		ProcessInstanceServiceImpl processInstanceService = new ProcessInstanceServiceImpl(this, processInstanceEntityService);
		processInstanceService.setCommandExecutor(commandExecutor);
		this.processInstanceService = processInstanceService;

		TaskServiceImpl taskService = new TaskServiceImpl(this, taskEntityService);
		taskService.setCommandExecutor(commandExecutor);
		this.taskService = taskService;

		RuntimeServiceImpl runtimeService = new RuntimeServiceImpl(this);
		runtimeService.setCommandExecutor(commandExecutor);
		this.runtimeService = runtimeService;
	}

	private ProcessDefinitionEntityService initProcessDefinitionEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<ProcessDefinitionMapper> factoryBean = new MapperFactoryBean<ProcessDefinitionMapper>();
		factoryBean.setMapperInterface(ProcessDefinitionMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();

		ProcessDefinitionMapper processDefinitionMapper = (ProcessDefinitionMapper) factoryBean.getObject();

		ProcessDefinitionEntityServiceImpl processDefinitionEntityService = new ProcessDefinitionEntityServiceImpl(processDefinitionMapper);

		return processDefinitionEntityService;
	}

	private HistoricTaskEntityService initHistoricTaskEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<HistoricTaskMapper> factoryBean = new MapperFactoryBean<HistoricTaskMapper>();
		factoryBean.setMapperInterface(HistoricTaskMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoricTaskMapper historicTaskMapper = (HistoricTaskMapper) factoryBean.getObject();

		HistoricTaskEntityServiceImpl historicTaskEntityService = new HistoricTaskEntityServiceImpl(historicTaskMapper);

		return historicTaskEntityService;
	}

	private ProcessInstanceEntityService initProcessInstanceEntityService(SqlSessionFactory sqlSessionFactory,
	        HistoricProcessInstanceEntityService historicProcessInstanceEntityService) throws Exception
	{
		MapperFactoryBean<ProcessInstanceMapper> factoryBean = new MapperFactoryBean<ProcessInstanceMapper>();
		factoryBean.setMapperInterface(ProcessInstanceMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		ProcessInstanceMapper processInstanceMapper = (ProcessInstanceMapper) factoryBean.getObject();

		ProcessInstanceEntityServiceImpl processInstanceEntityService = new ProcessInstanceEntityServiceImpl();
		processInstanceEntityService.setProcessInstanceMapper(processInstanceMapper);
		processInstanceEntityService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);

		return processInstanceEntityService;
	}

	private TaskEntityService initTaskEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<TaskMapper> factoryBean = new MapperFactoryBean<TaskMapper>();
		factoryBean.setMapperInterface(TaskMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		TaskMapper taskMapper = (TaskMapper) factoryBean.getObject();

		TaskEntityServiceImpl taskEntityService = new TaskEntityServiceImpl(taskMapper);

		return taskEntityService;
	}

	private HistoricProcessInstanceEntityService initHistoricProcessInstanceEntityService(SqlSessionFactory sqlSessionFactory)
	        throws Exception
	{
		MapperFactoryBean<HistoricProcessInstanceMapper> factoryBean = new MapperFactoryBean<HistoricProcessInstanceMapper>();
		factoryBean.setMapperInterface(HistoricProcessInstanceMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoricProcessInstanceMapper historicProcessInstanceMapper = (HistoricProcessInstanceMapper) factoryBean.getObject();

		HistoricProcessInstanceEntityServiceImpl historicProcessInstanceEntityService = new HistoricProcessInstanceEntityServiceImpl(
		        historicProcessInstanceMapper);

		return historicProcessInstanceEntityService;
	}

	/**
	 * 获取processDefinitionEntityService
	 * 
	 * @return
	 */
	public ProcessDefinitionEntityService getProcessDefinitionEntityService()
	{
		return processDefinitionEntityService;
	}

	/**
	 * 获取historicTaskEntityService
	 * 
	 * @return
	 */
	public HistoricTaskEntityService getHistoricTaskEntityService()
	{
		return historicTaskEntityService;
	}

	/**
	 * 获取taskEntityService
	 * 
	 * @return
	 */
	public TaskEntityService getTaskEntityService()
	{
		return taskEntityService;
	}

	/**
	 * 获取historicProcessInstanceEntityService
	 * 
	 * @return
	 */
	public HistoricProcessInstanceEntityService getHistoricProcessInstanceEntityService()
	{
		return historicProcessInstanceEntityService;
	}

	/**
	 * 获取processInstanceEntityService
	 * 
	 * @return
	 */
	public ProcessInstanceEntityService getProcessInstanceEntityService()
	{
		return processInstanceEntityService;
	}

	/**
	 * 设置applicationContext
	 * 
	 * @param applicationContext
	 */
	public void setApplicationContext(ApplicationContext applicationContext)
	{
		this.applicationContext = applicationContext;
	}

	/**
	 * 设置sqlSessionFactory
	 * 
	 * @param sqlSessionFactory
	 */
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory)
	{
		this.sqlSessionFactory = sqlSessionFactory;
	}

}
