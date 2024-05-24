package com.liteworkflow.engine.cfg;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.OrderService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessService;
import com.liteworkflow.engine.QueryService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.cache.CacheManager;
import com.liteworkflow.engine.cache.memory.MemoryCacheManager;
import com.liteworkflow.engine.core.ManagerServiceImpl;
import com.liteworkflow.engine.core.OrderServiceImpl;
import com.liteworkflow.engine.core.ProcessEngineImpl;
import com.liteworkflow.engine.core.ProcessServiceImpl;
import com.liteworkflow.engine.core.QueryServiceImpl;
import com.liteworkflow.engine.core.ServiceContext;
import com.liteworkflow.engine.core.TaskServiceImpl;
import com.liteworkflow.engine.helper.ClassHelper;
import com.liteworkflow.engine.helper.ConfigHelper;
import com.liteworkflow.engine.helper.StreamHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.helper.XmlHelper;
import com.liteworkflow.engine.spring.SpringContext;
import com.liteworkflow.order.service.CCOrderEntityService;
import com.liteworkflow.order.service.HistoryOrderEntityService;
import com.liteworkflow.order.service.OrderEntityService;
import com.liteworkflow.order.service.impl.CCOrderEntityServiceImpl;
import com.liteworkflow.order.service.impl.HistoryOrderEntityServiceImpl;
import com.liteworkflow.order.service.impl.OrderEntityServiceImpl;
import com.liteworkflow.process.service.ProcessEntityService;
import com.liteworkflow.process.service.impl.ProcessEntityServiceImpl;
import com.liteworkflow.task.service.HistoryTaskActorEntityService;
import com.liteworkflow.task.service.SurrogateEntityService;
import com.liteworkflow.task.service.TaskActorEntityService;
import com.liteworkflow.task.service.TaskEntityService;
import com.liteworkflow.task.service.impl.HistoryTaskActorEntityServiceImpl;
import com.liteworkflow.task.service.impl.HistoryTaskEntityServiceImpl;
import com.liteworkflow.task.service.impl.SurrogateEntityServiceImpl;
import com.liteworkflow.task.service.impl.TaskActorEntityServiceImpl;
import com.liteworkflow.task.service.impl.TaskEntityServiceImpl;
import com.liteworkflow.workitem.service.WorkItemEntityService;
import com.liteworkflow.workitem.service.impl.WorkItemEntityServiceImpl;

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

	private ProcessService processService;

	private QueryService queryService;

	private OrderService orderService;

	private TaskService taskService;

	private ManagerService managerService;

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

	private void initServices() throws Exception
	{
		ProcessEntityService processEntityService = new ProcessEntityServiceImpl(sqlSessionFactory);

		HistoryTaskActorEntityService historyTaskActorEntityService = new HistoryTaskActorEntityServiceImpl(sqlSessionFactory);
		HistoryTaskEntityServiceImpl historyTaskEntityService = new HistoryTaskEntityServiceImpl(sqlSessionFactory,
		        historyTaskActorEntityService);
		TaskActorEntityService taskActorEntityService = new TaskActorEntityServiceImpl(sqlSessionFactory);
		TaskEntityService taskEntityService = new TaskEntityServiceImpl(sqlSessionFactory);
		SurrogateEntityService surrogateEntityService = new SurrogateEntityServiceImpl(sqlSessionFactory);

		HistoryOrderEntityService historyOrderEntityService = new HistoryOrderEntityServiceImpl(sqlSessionFactory);
		CCOrderEntityService ccOrderEntityService = new CCOrderEntityServiceImpl(sqlSessionFactory);
		OrderEntityService orderEntityService = new OrderEntityServiceImpl(sqlSessionFactory, historyOrderEntityService);

		WorkItemEntityService workItemEntityService = new WorkItemEntityServiceImpl(sqlSessionFactory);

		ProcessServiceImpl processService = new ProcessServiceImpl();
		processService.setCacheManager(cacheManager);
		processService.setHistoryOrderEntityService(historyOrderEntityService);
		processService.setProcessEntityService(processEntityService);
		this.processService = processService;

		QueryServiceImpl queryService = new QueryServiceImpl();
		queryService.setCcOrderEntityService(ccOrderEntityService);
		queryService.setHistoryOrderEntityService(historyOrderEntityService);
		queryService.setHistoryTaskActorEntityService(historyTaskActorEntityService);
		queryService.setHistoryTaskEntityService(historyTaskEntityService);
		queryService.setOrderEntityService(orderEntityService);
		queryService.setTaskActorEntityService(taskActorEntityService);
		queryService.setTaskEntityService(taskEntityService);
		queryService.setWorkItemEntityService(workItemEntityService);
		this.queryService = queryService;

		OrderServiceImpl orderService = new OrderServiceImpl();
		orderService.setCcOrderEntityService(ccOrderEntityService);
		orderService.setHistoryOrderEntityService(historyOrderEntityService);
		orderService.setHistoryTaskEntityService(historyTaskEntityService);
		orderService.setOrderEntityService(orderEntityService);
		orderService.setTaskEntityService(taskEntityService);
		this.orderService = orderService;

		TaskServiceImpl taskService = new TaskServiceImpl();
		taskService.setHistoryTaskEntityService(historyTaskEntityService);
		taskService.setOrderEntityService(orderEntityService);
		taskService.setTaskActorEntityService(taskActorEntityService);
		taskService.setTaskEntityService(taskEntityService);
		taskService.setEngineConfiguration(this);
		this.taskService = taskService;

		ManagerServiceImpl managerService = new ManagerServiceImpl();
		managerService.setSurrogateEntityService(surrogateEntityService);
		this.managerService = managerService;
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
	public ProcessService getProcessService()
	{
		return this.processService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public QueryService getQueryService()
	{
		return this.queryService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public OrderService getOrderService()
	{
		return this.orderService;
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
	public ManagerService getManagerService()
	{
		return this.managerService;
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
