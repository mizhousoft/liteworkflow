package com.liteworkflow.engine.cfg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.liteworkflow.engine.HistoryService;
import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.cache.memory.MemoryCacheManager;
import com.liteworkflow.engine.impl.HistoryServiceImpl;
import com.liteworkflow.engine.impl.ManagerServiceImpl;
import com.liteworkflow.engine.impl.ProcessEngineImpl;
import com.liteworkflow.engine.impl.ProcessInstanceServiceImpl;
import com.liteworkflow.engine.impl.RepositoryServiceImpl;
import com.liteworkflow.engine.impl.RuntimeServiceImpl;
import com.liteworkflow.engine.impl.ServiceContext;
import com.liteworkflow.engine.impl.TaskServiceImpl;
import com.liteworkflow.engine.impl.context.SpringContext;
import com.liteworkflow.engine.persistence.mapper.CCProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.HistoricProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.HistoricTaskActorMapper;
import com.liteworkflow.engine.persistence.mapper.HistoricTaskMapper;
import com.liteworkflow.engine.persistence.mapper.ProcessDefinitionMapper;
import com.liteworkflow.engine.persistence.mapper.ProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.SurrogateMapper;
import com.liteworkflow.engine.persistence.mapper.TaskActorMapper;
import com.liteworkflow.engine.persistence.mapper.TaskMapper;
import com.liteworkflow.engine.persistence.service.CCProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskActorEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.SurrogateEntityService;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.persistence.service.impl.CCProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoricProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoricTaskActorEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoricTaskEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.ProcessDefinitionEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.ProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.SurrogateEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.TaskActorEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.TaskEntityServiceImpl;

/**
 * 只允许应用程序存在一个Configuration实例
 * 初始化服务上下文，查找流程引擎实现类并初始化依赖的服务
 * 
 * @author
 * @since 1.0
 */
public class ProcessEngineConfigurationImpl implements ProcessEngineConfiguration, ApplicationContextAware
{
	private static final Logger log = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);

	/**
	 * Spring上下文
	 */
	private ApplicationContext applicationContext;

	private CacheManager cacheManager = new MemoryCacheManager();

	private SqlSessionFactory sqlSessionFactory;

	private RepositoryService repositoryService;

	private ProcessInstanceService processInstanceService;

	private TaskService taskService;

	private HistoryService historyService;

	private ManagerService managerService;

	private RuntimeService runtimeService;

	private ProcessEngine processEngine;

	/**
	 * 构造ProcessEngine对象，用于api集成
	 * 通过SpringHelper调用
	 * 
	 * @return ProcessEngine
	 * @throws WorkflowException
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessEngine buildProcessEngine() throws Exception
	{
		log.info("ProcessEngine start......");

		ServiceContext.setContext(new SpringContext(applicationContext));

		initServices();

		this.processEngine = new ProcessEngineImpl(this);

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
	 * 获取processInstanceService
	 * 
	 * @return
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
	public ManagerService getManagerService()
	{
		return this.managerService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RuntimeService getRuntimeService()
	{
		return runtimeService;
	}

	private void initServices() throws Exception
	{
		ProcessDefinitionEntityService processDefinitionEntityService = initProcessDefinitionEntityService(sqlSessionFactory);

		HistoricTaskActorEntityService historicTaskActorEntityService = initHistoricTaskActorEntityService(sqlSessionFactory);
		HistoricTaskEntityService historicTaskEntityService = initHistoricTaskEntityService(sqlSessionFactory,
		        historicTaskActorEntityService);
		TaskActorEntityService taskActorEntityService = initTaskActorEntityService(sqlSessionFactory);
		TaskEntityService taskEntityService = initTaskEntityService(sqlSessionFactory);
		SurrogateEntityService surrogateEntityService = initSurrogateEntityService(sqlSessionFactory);

		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = initHistoricProcessInstanceEntityService(
		        sqlSessionFactory);
		CCProcessInstanceEntityService ccProcessInstanceEntityService = initCCProcessInstanceEntityService(sqlSessionFactory);
		ProcessInstanceEntityService processInstanceEntityService = initProcessInstanceEntityService(sqlSessionFactory,
		        historicProcessInstanceEntityService);

		RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
		repositoryService.setCacheManager(cacheManager);
		repositoryService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);
		repositoryService.setProcessDefinitionEntityService(processDefinitionEntityService);
		repositoryService.setEngineConfiguration(this);
		this.repositoryService = repositoryService;

		HistoryServiceImpl historyService = new HistoryServiceImpl();
		historyService.setCcProcessInstanceEntityService(ccProcessInstanceEntityService);
		historyService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);
		historyService.setHistoricTaskEntityService(historicTaskEntityService);
		historyService.setHistoricTaskActorEntityService(historicTaskActorEntityService);
		this.historyService = historyService;

		ProcessInstanceServiceImpl processInstanceService = new ProcessInstanceServiceImpl();
		processInstanceService.setCcProcessInstanceEntityService(ccProcessInstanceEntityService);
		processInstanceService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);
		processInstanceService.setHistoricTaskEntityService(historicTaskEntityService);
		processInstanceService.setProcessInstanceEntityService(processInstanceEntityService);
		processInstanceService.setTaskEntityService(taskEntityService);
		processInstanceService.setEngineConfiguration(this);
		this.processInstanceService = processInstanceService;

		TaskServiceImpl taskService = new TaskServiceImpl();
		taskService.setHistoricTaskEntityService(historicTaskEntityService);
		taskService.setProcessInstanceEntityService(processInstanceEntityService);
		taskService.setTaskActorEntityService(taskActorEntityService);
		taskService.setTaskEntityService(taskEntityService);
		taskService.setRepositoryService(repositoryService);
		taskService.setProcessInstanceService(processInstanceService);
		taskService.setEngineConfiguration(this);
		this.taskService = taskService;

		ManagerServiceImpl managerService = new ManagerServiceImpl();
		managerService.setSurrogateEntityService(surrogateEntityService);
		this.managerService = managerService;

		RuntimeServiceImpl runtimeService = new RuntimeServiceImpl(this);
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

	private TaskActorEntityService initTaskActorEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<TaskActorMapper> factoryBean = new MapperFactoryBean<TaskActorMapper>();
		factoryBean.setMapperInterface(TaskActorMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		TaskActorMapper taskActorMapper = (TaskActorMapper) factoryBean.getObject();

		TaskActorEntityServiceImpl taskActorEntityService = new TaskActorEntityServiceImpl();
		taskActorEntityService.setTaskActorMapper(taskActorMapper);

		return taskActorEntityService;
	}

	private HistoricTaskEntityService initHistoricTaskEntityService(SqlSessionFactory sqlSessionFactory,
	        HistoricTaskActorEntityService historicTaskActorEntityService) throws Exception
	{
		MapperFactoryBean<HistoricTaskMapper> factoryBean = new MapperFactoryBean<HistoricTaskMapper>();
		factoryBean.setMapperInterface(HistoricTaskMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoricTaskMapper historicTaskMapper = (HistoricTaskMapper) factoryBean.getObject();

		HistoricTaskEntityServiceImpl historicTaskEntityService = new HistoricTaskEntityServiceImpl();
		historicTaskEntityService.setHistoricTaskMapper(historicTaskMapper);
		historicTaskEntityService.setHistoricTaskActorEntityService(historicTaskActorEntityService);

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

		TaskEntityServiceImpl taskEntityService = new TaskEntityServiceImpl();
		taskEntityService.setTaskMapper(taskMapper);

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

		HistoricProcessInstanceEntityServiceImpl historicProcessInstanceEntityService = new HistoricProcessInstanceEntityServiceImpl();
		historicProcessInstanceEntityService.setHistoricProcessInstanceMapper(historicProcessInstanceMapper);

		return historicProcessInstanceEntityService;
	}

	private HistoricTaskActorEntityService initHistoricTaskActorEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<HistoricTaskActorMapper> factoryBean = new MapperFactoryBean<HistoricTaskActorMapper>();
		factoryBean.setMapperInterface(HistoricTaskActorMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoricTaskActorMapper historicTaskActorMapper = (HistoricTaskActorMapper) factoryBean.getObject();

		HistoricTaskActorEntityServiceImpl historicTaskActorEntityService = new HistoricTaskActorEntityServiceImpl();
		historicTaskActorEntityService.setHistoricTaskActorMapper(historicTaskActorMapper);

		return historicTaskActorEntityService;
	}

	private CCProcessInstanceEntityService initCCProcessInstanceEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<CCProcessInstanceMapper> factoryBean = new MapperFactoryBean<CCProcessInstanceMapper>();
		factoryBean.setMapperInterface(CCProcessInstanceMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		CCProcessInstanceMapper ccProcessInstanceMapper = (CCProcessInstanceMapper) factoryBean.getObject();

		CCProcessInstanceEntityServiceImpl ccProcessInstanceEntityService = new CCProcessInstanceEntityServiceImpl();
		ccProcessInstanceEntityService.setCcProcessInstanceMapper(ccProcessInstanceMapper);

		return ccProcessInstanceEntityService;
	}

	private SurrogateEntityService initSurrogateEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<SurrogateMapper> factoryBean = new MapperFactoryBean<SurrogateMapper>();
		factoryBean.setMapperInterface(SurrogateMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		SurrogateMapper surrogateMapper = (SurrogateMapper) factoryBean.getObject();

		SurrogateEntityServiceImpl surrogateEntityService = new SurrogateEntityServiceImpl();
		surrogateEntityService.setSurrogateMapper(surrogateMapper);

		return surrogateEntityService;
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
