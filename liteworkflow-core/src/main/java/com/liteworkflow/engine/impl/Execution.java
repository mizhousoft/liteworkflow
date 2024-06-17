package com.liteworkflow.engine.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * 流程执行过程中所传递的执行对象，其中包含流程定义、流程模型、流程实例对象、执行参数、返回的任务列表
 * 
 * @author yuqs
 * @since 1.0
 */
public class Execution implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3730741790729624400L;

	/**
	 * SnakerEngine holder
	 */
	private ProcessEngineConfiguration engineConfiguration;;

	/**
	 * 流程定义对象
	 */
	private ProcessDefinition process;

	/**
	 * 流程实例对象
	 */
	private ProcessInstance instance;

	/**
	 * 父流程实例
	 */
	private ProcessInstance parentInstance;

	/**
	 * 父流程实例节点名称
	 */
	private String parentNodeName;

	/**
	 * 子流程实例节点名称
	 */
	private String childOrderId;

	/**
	 * 执行参数
	 */
	private Map<String, Object> args;

	/**
	 * 操作人
	 */
	private String operator;

	/**
	 * 任务
	 */
	private Task task;

	/**
	 * 返回的任务列表
	 */
	private List<Task> tasks = new ArrayList<Task>();

	/**
	 * 是否已合并
	 * 针对join节点的处理
	 */
	private boolean isMerged = false;

	/**
	 * 用于产生子流程执行对象使用
	 * 
	 * @param execution
	 * @param process
	 * @param parentNodeName
	 */
	Execution(Execution execution, ProcessDefinition process, String parentNodeName)
	{
		if (execution == null || process == null || parentNodeName == null)
		{
			throw new ProcessException("构造Execution对象失败，请检查execution、process、parentNodeName是否为空");
		}
		this.engineConfiguration = execution.getEngineConfiguration();
		this.process = process;
		this.args = execution.getArgs();
		this.parentInstance = execution.getInstance();
		this.parentNodeName = parentNodeName;
		this.operator = execution.getOperator();
	}

	/**
	 * 构造函数，接收流程定义、流程实例对象、执行参数
	 * 
	 * @param process
	 * @param instance
	 * @param args
	 */
	public Execution(ProcessEngineConfiguration engineConfiguration, ProcessDefinition process, ProcessInstance instance,
	        Map<String, Object> args)
	{
		if (process == null || instance == null)
		{
			throw new ProcessException("构造Execution对象失败，请检查process、order是否为空");
		}
		this.engineConfiguration = engineConfiguration;
		this.process = process;
		this.instance = instance;
		this.args = args;
	}

	/**
	 * 根据当前执行对象execution、子流程定义process、当前节点名称产生子流程的执行对象
	 * 
	 * @param execution
	 * @param process
	 * @param parentNodeName
	 * @return
	 */
	public Execution createSubExecution(Execution execution, ProcessDefinition process, String parentNodeName)
	{
		return new Execution(execution, process, parentNodeName);
	}

	/**
	 * 获取流程定义对象
	 * 
	 * @return
	 */
	public ProcessDefinition getProcess()
	{
		return process;
	}

	/**
	 * 获取流程模型对象
	 * 
	 * @return
	 */
	public ProcessModel getModel()
	{
		return process.getModel();
	}

	/**
	 * 获取instance
	 * 
	 * @return
	 */
	public ProcessInstance getInstance()
	{
		return instance;
	}

	/**
	 * 获取执行参数
	 * 
	 * @return
	 */
	public Map<String, Object> getArgs()
	{
		return args;
	}

	/**
	 * 返回任务结果集
	 * 
	 * @return
	 */
	public List<Task> getTasks()
	{
		return tasks;
	}

	/**
	 * 添加任务集合
	 * 
	 * @param tasks
	 */
	public void addTasks(List<Task> tasks)
	{
		this.tasks.addAll(tasks);
	}

	/**
	 * 添加任务
	 * 
	 * @param task
	 */
	public void addTask(Task task)
	{
		this.tasks.add(task);
	}

	/**
	 * 返回当前操作人ID
	 * 
	 * @return
	 */
	public String getOperator()
	{
		return operator;
	}

	/**
	 * 设置当前操作人ID
	 * 
	 * @param operator
	 */
	public void setOperator(String operator)
	{
		this.operator = operator;
	}

	/**
	 * 返回任务
	 * 
	 * @return
	 */
	public Task getTask()
	{
		return task;
	}

	/**
	 * 设置任务
	 * 
	 * @param task
	 */
	public void setTask(Task task)
	{
		this.task = task;
	}

	/**
	 * 判断是否已经成功合并
	 * 
	 * @return
	 */
	public boolean isMerged()
	{
		return isMerged;
	}

	/**
	 * 设置是否为已合并
	 * 
	 * @param isMerged
	 */
	public void setMerged(boolean isMerged)
	{
		this.isMerged = isMerged;
	}

	/**
	 * 获取engineConfiguration
	 * 
	 * @return
	 */
	public ProcessEngineConfiguration getEngineConfiguration()
	{
		return engineConfiguration;
	}

	/**
	 * 获取parentInstance
	 * 
	 * @return
	 */
	public ProcessInstance getParentInstance()
	{
		return parentInstance;
	}

	public String getParentNodeName()
	{
		return parentNodeName;
	}

	public String getChildOrderId()
	{
		return childOrderId;
	}

	public void setChildOrderId(String childOrderId)
	{
		this.childOrderId = childOrderId;
	}
}
