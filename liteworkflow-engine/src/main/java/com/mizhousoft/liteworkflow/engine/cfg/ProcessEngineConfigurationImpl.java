package com.mizhousoft.liteworkflow.engine.cfg;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mizhousoft.liteworkflow.engine.HistoryService;
import com.mizhousoft.liteworkflow.engine.ProcessEngine;
import com.mizhousoft.liteworkflow.engine.ProcessEngineConfiguration;
import com.mizhousoft.liteworkflow.engine.ProcessInstanceService;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.RuntimeService;
import com.mizhousoft.liteworkflow.engine.TaskService;
import com.mizhousoft.liteworkflow.engine.cache.CacheManager;
import com.mizhousoft.liteworkflow.engine.cache.memory.MemoryCacheManager;
import com.mizhousoft.liteworkflow.engine.impl.CommandExecutor;
import com.mizhousoft.liteworkflow.engine.impl.CommandExecutorImpl;
import com.mizhousoft.liteworkflow.engine.impl.HistoryServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.ProcessEngineImpl;
import com.mizhousoft.liteworkflow.engine.impl.ProcessInstanceServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.RepositoryServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.RuntimeServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.TaskServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.ActivityBehaviorFactory;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.behavior.DefaultActivityBehaviorFactory;
import com.mizhousoft.liteworkflow.engine.impl.delegate.ListenerInvocation;
import com.mizhousoft.liteworkflow.engine.impl.delegate.invocation.ListenerInvocationImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.HistoricProcessInstanceMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.HistoricTaskMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.HistoricVariableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.ProcessDefinitionMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.ProcessInstanceMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.TaskMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.mapper.VariableMapper;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.DeploymentManager;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricTaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricVariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.TaskEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.VariableEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.DeploymentManagerImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.HistoricProcessInstanceEntityServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.HistoricTaskEntityServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.HistoricVariableEntityServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.ProcessDefEntityServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.ProcessInstanceEntityServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.TaskEntityServiceImpl;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.impl.VariableEntityServiceImpl;

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
	protected ApplicationContext applicationContext;

	/**
	 * CacheManager
	 */
	protected CacheManager cacheManager;

	/**
	 * SqlSessionFactory
	 */
	protected SqlSessionFactory sqlSessionFactory;

	/**
	 * RepositoryService
	 */
	protected RepositoryService repositoryService;

	/**
	 * ProcessInstanceService
	 */
	protected ProcessInstanceService processInstanceService;

	/**
	 * TaskService
	 */
	protected TaskService taskService;

	/**
	 * HistoryService
	 */
	protected HistoryService historyService;

	/**
	 * RuntimeService
	 */
	protected RuntimeService runtimeService;

	/**
	 * ProcessEngine
	 */
	protected ProcessEngine processEngine;

	/**
	 * ProcessDefEntityService
	 */
	protected ProcessDefEntityService processDefEntityService;

	/**
	 * DeploymentManager
	 */
	protected DeploymentManager deploymentManager;

	/**
	 * HistoricTaskEntityService
	 */
	protected HistoricTaskEntityService historicTaskEntityService;

	/**
	 * TaskEntityService
	 */
	protected TaskEntityService taskEntityService;

	/**
	 * HistoricProcessInstanceEntityService
	 */
	protected HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	/**
	 * ProcessInstanceEntityService
	 */
	protected ProcessInstanceEntityService processInstanceEntityService;

	/**
	 * VariableEntityService
	 */
	protected VariableEntityService variableEntityService;

	/**
	 * HistoricVariableEntityService
	 */
	protected HistoricVariableEntityService historicVariableEntityService;

	/**
	 * CommandExecutor
	 */
	protected CommandExecutor commandExecutor = new CommandExecutorImpl(this);

	/**
	 * ListenerInvocation
	 */
	protected ListenerInvocation listenerInvocation = new ListenerInvocationImpl(this);

	/**
	 * ActivityBehaviorFactory
	 */
	protected ActivityBehaviorFactory activityBehaviorFactory;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessEngine buildProcessEngine() throws Exception
	{
		LOG.info("Start to build ProcessEngine.");

		if (null == cacheManager)
		{
			cacheManager = new MemoryCacheManager();
		}

		initPersistenceServices();

		initOpenServices();

		initBehaviorFactory();

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
		ProcessDefinitionMapper processDefinitionMapper = createMapperObject(ProcessDefinitionMapper.class);
		this.processDefEntityService = new ProcessDefEntityServiceImpl(processDefinitionMapper);
		this.deploymentManager = new DeploymentManagerImpl(this, processDefEntityService, cacheManager);

		HistoricTaskMapper historicTaskMapper = createMapperObject(HistoricTaskMapper.class);
		this.historicTaskEntityService = new HistoricTaskEntityServiceImpl(historicTaskMapper);

		TaskMapper taskMapper = createMapperObject(TaskMapper.class);
		this.taskEntityService = new TaskEntityServiceImpl(taskMapper);

		HistoricProcessInstanceMapper historicProcessInstanceMapper = createMapperObject(HistoricProcessInstanceMapper.class);
		this.historicProcessInstanceEntityService = new HistoricProcessInstanceEntityServiceImpl(historicProcessInstanceMapper);

		ProcessInstanceMapper processInstanceMapper = createMapperObject(ProcessInstanceMapper.class);
		this.processInstanceEntityService = new ProcessInstanceEntityServiceImpl(processInstanceMapper);

		HistoricVariableMapper historicVariableMapper = createMapperObject(HistoricVariableMapper.class);
		this.historicVariableEntityService = new HistoricVariableEntityServiceImpl(historicVariableMapper);

		VariableMapper variableMapper = createMapperObject(VariableMapper.class);
		this.variableEntityService = new VariableEntityServiceImpl(variableMapper, historicVariableEntityService);
	}

	/**
	 * 初始化开放服务
	 * 
	 */
	private void initOpenServices()
	{
		RepositoryServiceImpl repositoryService = new RepositoryServiceImpl(this, deploymentManager, processDefEntityService);
		repositoryService.setCommandExecutor(commandExecutor);
		this.repositoryService = repositoryService;

		HistoryServiceImpl historyService = new HistoryServiceImpl(this, historicTaskEntityService, historicProcessInstanceEntityService,
		        historicVariableEntityService);
		historyService.setCommandExecutor(commandExecutor);
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

	/**
	 * 初始化行为工厂类
	 */
	public void initBehaviorFactory()
	{
		if (null == activityBehaviorFactory)
		{
			activityBehaviorFactory = new DefaultActivityBehaviorFactory();
		}
	}

	/**
	 * 创建持久层对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	private <T> T createMapperObject(Class<T> clazz) throws Exception
	{
		MapperFactoryBean<T> factoryBean = new MapperFactoryBean<T>();
		factoryBean.setMapperInterface(clazz);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		T mapperObject = (T) factoryBean.getObject();

		return mapperObject;
	}

	/**
	 * 获取processDefEntityService
	 * 
	 * @return
	 */
	public ProcessDefEntityService getProcessDefEntityService()
	{
		return processDefEntityService;
	}

	/**
	 * 获取deploymentManager
	 * 
	 * @return
	 */
	public DeploymentManager getDeploymentManager()
	{
		return deploymentManager;
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
	 * 获取variableEntityService
	 * 
	 * @return
	 */
	public VariableEntityService getVariableEntityService()
	{
		return variableEntityService;
	}

	/**
	 * 获取historicVariableEntityService
	 * 
	 * @return
	 */
	public HistoricVariableEntityService getHistoricVariableEntityService()
	{
		return historicVariableEntityService;
	}

	/**
	 * 获取commandExecutor
	 * 
	 * @return
	 */
	public CommandExecutor getCommandExecutor()
	{
		return commandExecutor;
	}

	/**
	 * 获取listenerInvocation
	 * 
	 * @return
	 */
	public ListenerInvocation getListenerInvocation()
	{
		return listenerInvocation;
	}

	/**
	 * 设置cacheManager
	 * 
	 * @param cacheManager
	 */
	public void setCacheManager(CacheManager cacheManager)
	{
		this.cacheManager = cacheManager;
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

	/**
	 * 获取activityBehaviorFactory
	 * 
	 * @return
	 */
	public ActivityBehaviorFactory getActivityBehaviorFactory()
	{
		return activityBehaviorFactory;
	}

	/**
	 * 设置activityBehaviorFactory
	 * 
	 * @param activityBehaviorFactory
	 */
	public void setActivityBehaviorFactory(ActivityBehaviorFactory activityBehaviorFactory)
	{
		this.activityBehaviorFactory = activityBehaviorFactory;
	}
}
