package com.liteworkflow.engine.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.OrderService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessService;
import com.liteworkflow.engine.QueryService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.process.entity.Process;
import com.liteworkflow.task.entity.Task;

/**
 * 基本的流程引擎实现类
 * 
 * @author yuqs
 * @since 1.0
 */
public class ProcessEngineImpl implements ProcessEngine
{
	private static final Logger log = LoggerFactory.getLogger(ProcessEngineImpl.class);

	/**
	 * Snaker配置对象
	 */
	protected ProcessEngineConfiguration configuration;

	/**
	 * 流程定义业务类
	 */
	protected ProcessService processService;

	/**
	 * 流程实例业务类
	 */
	protected OrderService orderService;

	/**
	 * 任务业务类
	 */
	protected TaskService taskService;

	/**
	 * 查询业务类
	 */
	protected QueryService queryService;

	/**
	 * 管理业务类
	 */
	protected ManagerService managerService;

	/**
	 * 构造函数
	 *
	 * @param configuration
	 */
	public ProcessEngineImpl(ProcessEngineConfiguration configuration)
	{
		super();
		this.configuration = configuration;

		this.managerService = configuration.getManagerService();
		this.orderService = configuration.getOrderService();
		this.processService = configuration.getProcessService();
		this.queryService = configuration.getQueryService();
		this.taskService = configuration.getTaskService();
	}

	/**
	 * 获取流程定义服务
	 */
	@Override
	public ProcessService getProcessService()
	{
		return processService;
	}

	/**
	 * 获取查询服务
	 */
	@Override
	public QueryService getQueryService()
	{
		return queryService;
	}

	/**
	 * 获取实例服务
	 * 
	 * @since 1.2.2
	 */
	@Override
	public OrderService getOrderService()
	{
		return orderService;
	}

	/**
	 * 获取任务服务
	 * 
	 * @since 1.2.2
	 */
	@Override
	public TaskService getTaskService()
	{
		return taskService;
	}

	/**
	 * 获取管理服务
	 * 
	 * @since 1.4
	 */
	@Override
	public ManagerService getManagerService()
	{
		return managerService;
	}

	/**
	 * 根据流程定义ID启动流程实例
	 */
	public Order startInstanceById(String id)
	{
		return startInstanceById(id, null, null);
	}

	/**
	 * 根据流程定义ID，操作人ID启动流程实例
	 */
	public Order startInstanceById(String id, String operator)
	{
		return startInstanceById(id, operator, null);
	}

	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 */
	public Order startInstanceById(String id, String operator, Map<String, Object> args)
	{
		if (args == null)
			args = new HashMap<String, Object>();
		Process process = getProcessService().getProcessById(id);
		getProcessService().check(process, id);
		return startProcess(process, operator, args);
	}

	/**
	 * 根据流程名称启动流程实例
	 * 
	 * @since 1.3
	 */
	public Order startInstanceByName(String name)
	{
		return startInstanceByName(name, null, null, null);
	}

	/**
	 * 根据流程名称、版本号启动流程实例
	 * 
	 * @since 1.3
	 */
	public Order startInstanceByName(String name, Integer version)
	{
		return startInstanceByName(name, version, null, null);
	}

	/**
	 * 根据流程名称、版本号、操作人启动流程实例
	 * 
	 * @since 1.3
	 */
	public Order startInstanceByName(String name, Integer version, String operator)
	{
		return startInstanceByName(name, version, operator, null);
	}

	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * 
	 * @since 1.3
	 */
	public Order startInstanceByName(String name, Integer version, String operator, Map<String, Object> args)
	{
		if (args == null)
			args = new HashMap<String, Object>();
		Process process = getProcessService().getProcessByVersion(name, version);
		getProcessService().check(process, name);
		return startProcess(process, operator, args);
	}

	private Order startProcess(Process process, String operator, Map<String, Object> args)
	{
		Execution execution = execute(process, operator, args, null, null);
		if (process.getModel() != null)
		{
			StartModel start = process.getModel().getStart();
			AssertHelper.notNull(start, "流程定义[name=" + process.getName() + ", version=" + process.getVersion() + "]没有开始节点");
			start.execute(execution);
		}

		return execution.getOrder();
	}

	/**
	 * 根据父执行对象启动子流程实例（用于启动子流程）
	 */
	public Order startInstanceByExecution(Execution execution)
	{
		Process process = execution.getProcess();
		StartModel start = process.getModel().getStart();
		AssertHelper.notNull(start, "流程定义[id=" + process.getId() + "]没有开始节点");

		Execution current = execute(process, execution.getOperator(), execution.getArgs(), execution.getParentOrder().getId(),
		        execution.getParentNodeName());
		start.execute(current);
		return current.getOrder();
	}

	/**
	 * 创建流程实例，并返回执行对象
	 * 
	 * @param process 流程定义
	 * @param operator 操作人
	 * @param args 参数列表
	 * @param parentId 父流程实例id
	 * @param parentNodeName 启动子流程的父流程节点名称
	 * @return Execution
	 */
	private Execution execute(Process process, String operator, Map<String, Object> args, String parentId, String parentNodeName)
	{
		Order order = getOrderService().createOrder(process, operator, args, parentId, parentNodeName);

		log.debug("创建流程实例对象:" + order);

		Execution current = new Execution(this, process, order, args);
		current.setOperator(operator);
		return current;
	}

	/**
	 * 根据任务主键ID执行任务
	 */
	public List<Task> executeTask(String taskId)
	{
		return executeTask(taskId, null);
	}

	/**
	 * 根据任务主键ID，操作人ID执行任务
	 */
	public List<Task> executeTask(String taskId, String operator)
	{
		return executeTask(taskId, operator, null);
	}

	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务
	 */
	public List<Task> executeTask(String taskId, String operator, Map<String, Object> args)
	{
		// 完成任务，并且构造执行对象
		Execution execution = execute(taskId, operator, args);
		if (execution == null)
			return Collections.emptyList();
		ProcessModel model = execution.getProcess().getModel();
		if (model != null)
		{
			NodeModel nodeModel = model.getNode(execution.getTask().getTaskName());
			// 将执行对象交给该任务对应的节点模型执行
			nodeModel.execute(execution);
		}
		return execution.getTasks();
	}

	/**
	 * 根据任务主键ID，操作人ID，参数列表执行任务，并且根据nodeName跳转到任意节点
	 * 1、nodeName为null时，则驳回至上一步处理
	 * 2、nodeName不为null时，则任意跳转，即动态创建转移
	 */
	public List<Task> executeAndJumpTask(String taskId, String operator, Map<String, Object> args, String nodeName)
	{
		Execution execution = execute(taskId, operator, args);
		if (execution == null)
			return Collections.emptyList();
		ProcessModel model = execution.getProcess().getModel();
		AssertHelper.notNull(model, "当前任务未找到流程定义模型");
		if (StringHelper.isEmpty(nodeName))
		{
			Task newTask = getTaskService().rejectTask(model, execution.getTask());
			execution.addTask(newTask);
		}
		else
		{
			NodeModel nodeModel = model.getNode(nodeName);
			AssertHelper.notNull(nodeModel, "根据节点名称[" + nodeName + "]无法找到节点模型");
			// 动态创建转移对象，由转移对象执行execution实例
			TransitionModel tm = new TransitionModel();
			tm.setTarget(nodeModel);
			tm.setEnabled(true);
			tm.execute(execution);
		}

		return execution.getTasks();
	}

	/**
	 * 根据流程实例ID，操作人ID，参数列表按照节点模型model创建新的自由任务
	 */
	public List<Task> createFreeTask(String orderId, String operator, Map<String, Object> args, TaskModel model)
	{
		Order order = getQueryService().getOrder(orderId);
		AssertHelper.notNull(order, "指定的流程实例[id=" + orderId + "]已完成或不存在");
		order.setLastUpdator(operator);
		order.setLastUpdateTime(DateHelper.getTime());
		Process process = getProcessService().getProcessById(order.getProcessId());
		Execution execution = new Execution(this, process, order, args);
		execution.setOperator(operator);
		return getTaskService().createTask(model, execution);
	}

	/**
	 * 根据任务主键ID，操作人ID，参数列表完成任务，并且构造执行对象
	 * 
	 * @param taskId 任务id
	 * @param operator 操作人
	 * @param args 参数列表
	 * @return Execution
	 */
	private Execution execute(String taskId, String operator, Map<String, Object> args)
	{
		if (args == null)
			args = new HashMap<String, Object>();
		Task task = getTaskService().complete(taskId, operator, args);

		log.debug("任务[taskId=" + taskId + "]已完成");

		Order order = getQueryService().getOrder(task.getOrderId());
		AssertHelper.notNull(order, "指定的流程实例[id=" + task.getOrderId() + "]已完成或不存在");
		order.setLastUpdator(operator);
		order.setLastUpdateTime(DateHelper.getTime());
		getOrderService().updateOrder(order);
		// 协办任务完成不产生执行对象
		if (!task.isMajor())
		{
			return null;
		}
		Map<String, Object> orderMaps = order.getVariableMap();
		if (orderMaps != null)
		{
			for (Map.Entry<String, Object> entry : orderMaps.entrySet())
			{
				if (args.containsKey(entry.getKey()))
				{
					continue;
				}
				args.put(entry.getKey(), entry.getValue());
			}
		}
		Process process = getProcessService().getProcessById(order.getProcessId());
		Execution execution = new Execution(this, process, order, args);
		execution.setOperator(operator);
		execution.setTask(task);
		return execution;
	}
}
