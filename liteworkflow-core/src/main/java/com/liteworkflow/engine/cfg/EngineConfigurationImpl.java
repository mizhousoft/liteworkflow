package com.liteworkflow.engine.cfg;

import java.io.InputStream;
import java.util.Properties;

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

import com.liteworkflow.WorkflowException;
import com.liteworkflow.engine.EngineConfiguration;
import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.OrderService;
import com.liteworkflow.engine.ProcessService;
import com.liteworkflow.engine.QueryService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.cache.memory.MemoryCacheManager;
import com.liteworkflow.engine.core.ManagerServiceImpl;
import com.liteworkflow.engine.core.OrderServiceImpl;
import com.liteworkflow.engine.core.ProcessServiceImpl;
import com.liteworkflow.engine.core.QueryServiceImpl;
import com.liteworkflow.engine.core.ServiceContext;
import com.liteworkflow.engine.core.ProcessEngineImpl;
import com.liteworkflow.engine.core.TaskServiceImpl;
import com.liteworkflow.engine.helper.ClassHelper;
import com.liteworkflow.engine.helper.ConfigHelper;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.helper.XmlHelper;
import com.liteworkflow.engine.spring.SpringContext;
import com.liteworkflow.order.mapper.CCOrderMapper;
import com.liteworkflow.order.mapper.HistoryOrderMapper;
import com.liteworkflow.order.mapper.OrderMapper;
import com.liteworkflow.order.service.CCOrderEntityService;
import com.liteworkflow.order.service.HistoryOrderEntityService;
import com.liteworkflow.order.service.OrderEntityService;
import com.liteworkflow.order.service.impl.CCOrderEntityServiceImpl;
import com.liteworkflow.order.service.impl.HistoryOrderEntityServiceImpl;
import com.liteworkflow.order.service.impl.OrderEntityServiceImpl;
import com.liteworkflow.process.mapper.ProcessMapper;
import com.liteworkflow.process.service.ProcessEntityService;
import com.liteworkflow.process.service.impl.ProcessEntityServiceImpl;
import com.liteworkflow.task.mapper.HistoryTaskActorMapper;
import com.liteworkflow.task.mapper.HistoryTaskMapper;
import com.liteworkflow.task.mapper.SurrogateMapper;
import com.liteworkflow.task.mapper.TaskActorMapper;
import com.liteworkflow.task.mapper.TaskMapper;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.task.service.HistoryTaskEntityService;
import com.liteworkflow.task.service.SurrogateEntityService;
import com.liteworkflow.task.service.TaskActorEntityService;
import com.liteworkflow.task.service.TaskEntityService;
import com.liteworkflow.task.service.impl.HistoryTaskActorEntityServiceImpl;
import com.liteworkflow.task.service.impl.HistoryTaskEntityServiceImpl;
import com.liteworkflow.task.service.impl.SurrogateEntityServiceImpl;
import com.liteworkflow.task.service.impl.TaskActorEntityServiceImpl;
import com.liteworkflow.task.service.impl.TaskEntityServiceImpl;
import com.liteworkflow.workitem.mapper.WorkItemMapper;
import com.liteworkflow.workitem.service.WorkItemEntityService;
import com.liteworkflow.workitem.service.impl.WorkItemEntityServiceImpl;

/**
 * 只允许应用程序存在一个Configuration实例
 * 初始化服务上下文，查找流程引擎实现类并初始化依赖的服务
 * 
 * @author yuqs
 * @since 1.0
 */
public class EngineConfigurationImpl implements EngineConfiguration, ApplicationContextAware
{
	/**
	 * 
	 */
	private static final Logger log = LoggerFactory.getLogger(EngineConfigurationImpl.class);

	private static final String BASE_CONFIG_FILE = "base.config.xml";

	private final static String USER_CONFIG_FILE = "snaker.xml";

	/**
	 * Spring上下文
	 */
	private ApplicationContext applicationContext;

	private SqlSessionFactory sqlSessionFactory;

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

		CacheManager cacheManager = new MemoryCacheManager();
		ProcessEntityService processEntityService = getProcessEntityService(sqlSessionFactory);
		HistoryOrderEntityService historyOrderEntityService = getHistoryOrderEntityService(sqlSessionFactory);
		OrderEntityService orderEntityService = getOrderEntityService(sqlSessionFactory, historyOrderEntityService);
		CCOrderEntityService ccOrderEntityService = getCCOrderEntityService(sqlSessionFactory);
		TaskEntityService taskEntityService = getTaskEntityService(sqlSessionFactory);
		HistoryTaskActorEntityService historyTaskActorEntityService = getHistoryTaskActorEntityService(sqlSessionFactory);
		HistoryTaskEntityService historyTaskEntityService = getHistoryTaskEntityService(sqlSessionFactory, historyTaskActorEntityService);
		SurrogateEntityService surrogateEntityService = getSurrogateEntityService(sqlSessionFactory);
		TaskActorEntityService taskActorEntityService = getTaskActorEntityService(sqlSessionFactory);
		WorkItemEntityService workItemEntityService = getWorkItemEntityService(sqlSessionFactory);

		ProcessService processService = getProcessService(cacheManager, processEntityService, historyOrderEntityService);
		QueryService queryService = getQueryService(taskEntityService, taskActorEntityService, historyTaskEntityService, orderEntityService,
		        historyOrderEntityService, historyTaskActorEntityService, ccOrderEntityService, workItemEntityService);
		OrderService orderService = getOrderService(orderEntityService, ccOrderEntityService, historyOrderEntityService, taskEntityService,
		        historyTaskEntityService);
		TaskService taskService = getTaskService(taskEntityService, taskActorEntityService, historyTaskEntityService, orderEntityService);
		ManagerService managerService = getManagerService(surrogateEntityService);

		parser();

		ProcessEngineImpl snakerEngine = new ProcessEngineImpl();
		snakerEngine.setConfiguration(this);
		snakerEngine.setManagerService(managerService);
		snakerEngine.setOrderService(orderService);
		snakerEngine.setProcessService(processService);
		snakerEngine.setQueryService(queryService);
		snakerEngine.setTaskService(taskService);

		return snakerEngine;
	}

	public ProcessService getProcessService(CacheManager cacheManager, ProcessEntityService processEntityService,
	        HistoryOrderEntityService historyOrderEntityService)
	{
		ProcessServiceImpl processService = new ProcessServiceImpl();
		processService.setCacheManager(cacheManager);
		processService.setHistoryOrderEntityService(historyOrderEntityService);
		processService.setProcessEntityService(processEntityService);

		return processService;
	}

	public OrderService getOrderService(OrderEntityService orderEntityService, CCOrderEntityService ccOrderEntityService,
	        HistoryOrderEntityService historyOrderEntityService, TaskEntityService taskEntityService,
	        HistoryTaskEntityService historyTaskEntityService)
	{
		OrderServiceImpl orderService = new OrderServiceImpl();
		orderService.setCcOrderEntityService(ccOrderEntityService);
		orderService.setHistoryOrderEntityService(historyOrderEntityService);
		orderService.setHistoryTaskEntityService(historyTaskEntityService);
		orderService.setOrderEntityService(orderEntityService);
		orderService.setTaskEntityService(taskEntityService);

		return orderService;
	}

	public TaskService getTaskService(TaskEntityService taskEntityService, TaskActorEntityService taskActorEntityService,
	        HistoryTaskEntityService historyTaskEntityService, OrderEntityService orderEntityService)
	{
		TaskServiceImpl taskService = new TaskServiceImpl();
		taskService.setHistoryTaskEntityService(historyTaskEntityService);
		taskService.setOrderEntityService(orderEntityService);
		taskService.setTaskActorEntityService(taskActorEntityService);
		taskService.setTaskEntityService(taskEntityService);

		return taskService;
	}

	public ManagerService getManagerService(SurrogateEntityService surrogateEntityService)
	{
		ManagerServiceImpl managerService = new ManagerServiceImpl();
		managerService.setSurrogateEntityService(surrogateEntityService);

		return managerService;
	}

	public QueryService getQueryService(TaskEntityService taskEntityService, TaskActorEntityService taskActorEntityService,
	        HistoryTaskEntityService historyTaskEntityService, OrderEntityService orderEntityService,
	        HistoryOrderEntityService historyOrderEntityService, HistoryTaskActorEntityService historyTaskActorEntityService,
	        CCOrderEntityService ccOrderEntityService, WorkItemEntityService workItemEntityService)
	{
		QueryServiceImpl queryService = new QueryServiceImpl();
		queryService.setCcOrderEntityService(ccOrderEntityService);
		queryService.setHistoryOrderEntityService(historyOrderEntityService);
		queryService.setHistoryTaskActorEntityService(historyTaskActorEntityService);
		queryService.setHistoryTaskEntityService(historyTaskEntityService);
		queryService.setOrderEntityService(orderEntityService);
		queryService.setTaskActorEntityService(taskActorEntityService);
		queryService.setTaskEntityService(taskEntityService);
		queryService.setWorkItemEntityService(workItemEntityService);

		return queryService;
	}

	private TaskActorEntityService getTaskActorEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
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

	public HistoryTaskEntityService getHistoryTaskEntityService(SqlSessionFactory sqlSessionFactory,
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

	public ProcessEntityService getProcessEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<ProcessMapper> factoryBean = new MapperFactoryBean<ProcessMapper>();
		factoryBean.setMapperInterface(ProcessMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		ProcessMapper processMapper = (ProcessMapper) factoryBean.getObject();

		ProcessEntityServiceImpl processEntityService = new ProcessEntityServiceImpl();
		processEntityService.setProcessMapper(processMapper);

		return processEntityService;
	}

	public OrderEntityService getOrderEntityService(SqlSessionFactory sqlSessionFactory,
	        HistoryOrderEntityService historyOrderEntityService) throws Exception
	{
		MapperFactoryBean<OrderMapper> factoryBean = new MapperFactoryBean<OrderMapper>();
		factoryBean.setMapperInterface(OrderMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		OrderMapper orderMapper = (OrderMapper) factoryBean.getObject();

		OrderEntityServiceImpl orderEntityService = new OrderEntityServiceImpl();
		orderEntityService.setOrderMapper(orderMapper);
		orderEntityService.setHistoryOrderEntityService(historyOrderEntityService);

		return orderEntityService;
	}

	public TaskEntityService getTaskEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
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

	public HistoryOrderEntityService getHistoryOrderEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
	{
		MapperFactoryBean<HistoryOrderMapper> factoryBean = new MapperFactoryBean<HistoryOrderMapper>();
		factoryBean.setMapperInterface(HistoryOrderMapper.class);
		factoryBean.setAddToConfig(true);
		factoryBean.setSqlSessionFactory(sqlSessionFactory);
		factoryBean.afterPropertiesSet();
		HistoryOrderMapper historyOrderMapper = (HistoryOrderMapper) factoryBean.getObject();

		HistoryOrderEntityServiceImpl historyOrderEntityService = new HistoryOrderEntityServiceImpl();
		historyOrderEntityService.setHistoryOrderMapper(historyOrderMapper);

		return historyOrderEntityService;
	}

	public HistoryTaskActorEntityService getHistoryTaskActorEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
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

	public CCOrderEntityService getCCOrderEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
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

	public SurrogateEntityService getSurrogateEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
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

	public WorkItemEntityService getWorkItemEntityService(SqlSessionFactory sqlSessionFactory) throws Exception
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
			e.printStackTrace();
			throw new WorkflowException("资源解析失败，请检查配置文件[" + resource + "]", e.getCause());
		}
	}

	/**
	 * 可装载自定义的属性配置文件
	 * 
	 * @param fileName 属性文件名称
	 * @return Configuration
	 */
	public EngineConfigurationImpl initProperties(String fileName)
	{
		ConfigHelper.loadProperties(fileName);
		return this;
	}

	/**
	 * 可装载已有的属性对象
	 * 
	 * @param properties 属性对象
	 * @return Configuration
	 */
	public EngineConfigurationImpl initProperties(Properties properties)
	{
		ConfigHelper.loadProperties(properties);
		return this;
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
