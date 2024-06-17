package com.liteworkflow.engine.cfg;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.liteworkflow.ProcessException;
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
import com.liteworkflow.engine.helper.ClassHelper;
import com.liteworkflow.engine.helper.ConfigHelper;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.helper.XmlHelper;
import com.liteworkflow.engine.impl.HistoryServiceImpl;
import com.liteworkflow.engine.impl.ManagerServiceImpl;
import com.liteworkflow.engine.impl.ProcessEngineImpl;
import com.liteworkflow.engine.impl.ProcessInstanceServiceImpl;
import com.liteworkflow.engine.impl.RepositoryServiceImpl;
import com.liteworkflow.engine.impl.RuntimeServiceImpl;
import com.liteworkflow.engine.impl.ServiceContext;
import com.liteworkflow.engine.impl.TaskServiceImpl;
import com.liteworkflow.engine.impl.context.SpringContext;
import com.liteworkflow.engine.persistence.mapper.CCOrderMapper;
import com.liteworkflow.engine.persistence.mapper.HistoricProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.HistoryTaskActorMapper;
import com.liteworkflow.engine.persistence.mapper.HistoryTaskMapper;
import com.liteworkflow.engine.persistence.mapper.ProcessDefinitionMapper;
import com.liteworkflow.engine.persistence.mapper.ProcessInstanceMapper;
import com.liteworkflow.engine.persistence.mapper.SurrogateMapper;
import com.liteworkflow.engine.persistence.mapper.TaskActorMapper;
import com.liteworkflow.engine.persistence.mapper.TaskMapper;
import com.liteworkflow.engine.persistence.mapper.WorkItemMapper;
import com.liteworkflow.engine.persistence.service.CCOrderEntityService;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoryTaskActorEntityService;
import com.liteworkflow.engine.persistence.service.HistoryTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.SurrogateEntityService;
import com.liteworkflow.engine.persistence.service.TaskActorEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
import com.liteworkflow.engine.persistence.service.WorkItemEntityService;
import com.liteworkflow.engine.persistence.service.impl.CCOrderEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoricProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoryTaskActorEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.HistoryTaskEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.ProcessDefinitionEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.ProcessInstanceEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.SurrogateEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.TaskActorEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.TaskEntityServiceImpl;
import com.liteworkflow.engine.persistence.service.impl.WorkItemEntityServiceImpl;

/**
 * 只允许应用程序存在一个Configuration实例
 * 初始化服务上下文，查找流程引擎实现类并初始化依赖的服务
 * 
 * @author yuqs
 * @since 1.0
 */
public class ProcessEngineConfigurationImpl implements ProcessEngineConfiguration, ApplicationContextAware
{
	private static final Logger log = LoggerFactory.getLogger(ProcessEngineConfigurationImpl.class);

	private static final String BASE_CONFIG_FILE = "base.config.xml";

	private final static String USER_CONFIG_FILE = "snaker.xml";

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
	 * 构造SnakerEngine对象，用于api集成
	 * 通过SpringHelper调用
	 * 
	 * @return SnakerEngine
	 * @throws WorkflowException
	 */
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessEngine buildProcessEngine() throws Exception
	{
		log.info("SnakerEngine start......");

		ServiceContext.setContext(new SpringContext(applicationContext));

		initServices();

		parser();

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

		HistoryTaskActorEntityService historyTaskActorEntityService = initHistoryTaskActorEntityService(sqlSessionFactory);
		HistoryTaskEntityService historyTaskEntityService = initHistoryTaskEntityService(sqlSessionFactory, historyTaskActorEntityService);
		TaskActorEntityService taskActorEntityService = initTaskActorEntityService(sqlSessionFactory);
		TaskEntityService taskEntityService = initTaskEntityService(sqlSessionFactory);
		SurrogateEntityService surrogateEntityService = initSurrogateEntityService(sqlSessionFactory);

		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = initHistoricProcessInstanceEntityService(
		        sqlSessionFactory);
		CCOrderEntityService ccOrderEntityService = initCCOrderEntityService(sqlSessionFactory);
		ProcessInstanceEntityService processInstanceEntityService = initProcessInstanceEntityService(sqlSessionFactory,
		        historicProcessInstanceEntityService);

		WorkItemEntityService workItemEntityService = initWorkItemEntityService(sqlSessionFactory);

		RepositoryServiceImpl repositoryService = new RepositoryServiceImpl();
		repositoryService.setCacheManager(cacheManager);
		repositoryService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);
		repositoryService.setProcessDefinitionEntityService(processDefinitionEntityService);
		repositoryService.setEngineConfiguration(this);
		this.repositoryService = repositoryService;

		HistoryServiceImpl historyService = new HistoryServiceImpl();
		historyService.setCcOrderEntityService(ccOrderEntityService);
		historyService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);
		historyService.setHistoryTaskEntityService(historyTaskEntityService);
		historyService.setWorkItemEntityService(workItemEntityService);
		historyService.setHistoryTaskActorEntityService(historyTaskActorEntityService);
		historyService.setHistoryTaskEntityService(historyTaskEntityService);
		this.historyService = historyService;
		
		ProcessInstanceServiceImpl processInstanceService = new ProcessInstanceServiceImpl();
		processInstanceService.setCcOrderEntityService(ccOrderEntityService);
		processInstanceService.setHistoricProcessInstanceEntityService(historicProcessInstanceEntityService);
		processInstanceService.setHistoryTaskEntityService(historyTaskEntityService);
		processInstanceService.setProcessInstanceEntityService(processInstanceEntityService);
		processInstanceService.setTaskEntityService(taskEntityService);
		processInstanceService.setEngineConfiguration(this);
		this.processInstanceService = processInstanceService;

		TaskServiceImpl taskService = new TaskServiceImpl();
		taskService.setHistoryTaskEntityService(historyTaskEntityService);
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

		RuntimeServiceImpl runtimeService = new RuntimeServiceImpl(this, workItemEntityService);
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

		ProcessDefinitionEntityServiceImpl processDefinitionEntityService = new ProcessDefinitionEntityServiceImpl();
		processDefinitionEntityService.setprocessDefinitionMapper(processDefinitionMapper);

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

	private HistoryTaskEntityService initHistoryTaskEntityService(SqlSessionFactory sqlSessionFactory,
	        HistoryTaskActorEntityService historyTaskActorEntityService) throws Exception
	{
		MapperFactoryBean<HistoryTaskMapper> factoryBean = new MapperFactoryBean<HistoryTaskMapper>();
		factoryBean.setMapperInterface(HistoryTaskMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoryTaskMapper historyTaskMapper = (HistoryTaskMapper) factoryBean.getObject();

		HistoryTaskEntityServiceImpl historyTaskEntityService = new HistoryTaskEntityServiceImpl();
		historyTaskEntityService.setHistoryTaskMapper(historyTaskMapper);
		historyTaskEntityService.setHistoryTaskActorEntityService(historyTaskActorEntityService);

		return historyTaskEntityService;
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

	private HistoryTaskActorEntityService initHistoryTaskActorEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<HistoryTaskActorMapper> factoryBean = new MapperFactoryBean<HistoryTaskActorMapper>();
		factoryBean.setMapperInterface(HistoryTaskActorMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoryTaskActorMapper historyTaskActorMapper = (HistoryTaskActorMapper) factoryBean.getObject();

		HistoryTaskActorEntityServiceImpl historyTaskActorEntityService = new HistoryTaskActorEntityServiceImpl();
		historyTaskActorEntityService.setHistoryTaskActorMapper(historyTaskActorMapper);

		return historyTaskActorEntityService;
	}

	private CCOrderEntityService initCCOrderEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<CCOrderMapper> factoryBean = new MapperFactoryBean<CCOrderMapper>();
		factoryBean.setMapperInterface(CCOrderMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		CCOrderMapper ccOrderMapper = (CCOrderMapper) factoryBean.getObject();

		CCOrderEntityServiceImpl ccOrderEntityService = new CCOrderEntityServiceImpl();
		ccOrderEntityService.setCcOrderMapper(ccOrderMapper);

		return ccOrderEntityService;
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

	private WorkItemEntityService initWorkItemEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<WorkItemMapper> factoryBean = new MapperFactoryBean<WorkItemMapper>();
		factoryBean.setMapperInterface(WorkItemMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		WorkItemMapper workItemMapper = (WorkItemMapper) factoryBean.getObject();

		WorkItemEntityServiceImpl workItemEntityService = new WorkItemEntityServiceImpl();
		workItemEntityService.setWorkItemMapper(workItemMapper);

		return workItemEntityService;
	}

	/**
	 * 依次解析框架固定的配置及用户自定义的配置
	 * 固定配置文件:base.config.xml
	 * 扩展配置文件:ext.config.xml
	 * 用户自定义配置文件:snaker.xml
	 */
	public void parser()
	{
		log.debug("Service parsing start......");

		// 默认使用snaker.xml配置自定义的bean
		String config = ConfigHelper.getProperty("config");
		if (StringHelper.isEmpty(config))
		{
			config = USER_CONFIG_FILE;
		}
		parser(config);
		parser(BASE_CONFIG_FILE);

		log.debug("Service parsing finish......");
	}

	/**
	 * 解析给定resource配置，并注册到ServiceContext上下文中
	 * 
	 * @param resource 资源
	 */
	private void parser(String resource)
	{
		// 解析所有配置节点，并实例化class指定的类
		DocumentBuilder documentBuilder = XmlHelper.createDocumentBuilder();
		try
		{
			if (documentBuilder != null)
			{
				InputStream input = StreamHelper.openStream(resource);
				if (input == null)
					return;
				Document doc = documentBuilder.parse(input);
				Element configElement = doc.getDocumentElement();
				NodeList nodeList = configElement.getChildNodes();
				int nodeSize = nodeList.getLength();
				for (int i = 0; i < nodeSize; i++)
				{
					Node node = nodeList.item(i);
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						Element element = (Element) node;
						String name = element.getAttribute("name");
						String className = element.getAttribute("class");

						if (StringHelper.isEmpty(name))
						{
							name = className;
						}
						if (ServiceContext.exist(name))
						{
							log.warn("Duplicate name is:" + name);
							continue;
						}

						Class<?> clazz = ClassHelper.loadClass(className);
						ServiceContext.put(name, clazz);
					}
				}
			}
		}
		catch (Exception e)
		{
			throw new ProcessException("资源解析失败，请检查配置文件[" + resource + "]", e.getCause());
		}
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
