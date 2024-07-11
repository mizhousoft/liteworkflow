package com.liteworkflow.engine.impl;

import java.util.Map;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;

/**
 * 流程执行过程中所传递的执行对象，其中包含流程定义、流程模型、流程实例对象、执行参数、返回的任务列表
 * 
 * @author
 * @since 1.0
 */
public class Execution
{
	/**
	 * ProcessEngineConfiguration
	 */
	private ProcessEngineConfigurationImpl engineConfiguration;

	/**
	 * 流程定义对象
	 */
	private ProcessDefinition processDefinition;

	/**
	 * 流程实例对象
	 */
	private ProcessInstance processInstance;

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
	private String childInstanceId;

	/**
	 * 执行参数
	 */
	private Map<String, Object> args;

	/**
	 * 任务
	 */
	private Task task;

	/**
	 * 是否已合并
	 * 针对join节点的处理
	 */
	private boolean isMerged = false;

	/**
	 * 用于产生子流程执行对象使用
	 * 
	 * @param execution
	 * @param processDefinition
	 * @param parentNodeName
	 */
	Execution(Execution execution, ProcessDefinition processDefinition, String parentNodeName)
	{
		if (execution == null || processDefinition == null || parentNodeName == null)
		{
			throw new WorkFlowException("构造Execution对象失败，请检查execution、process、parentNodeName是否为空");
		}
		this.engineConfiguration = execution.getEngineConfiguration();
		this.processDefinition = processDefinition;
		this.args = execution.getArgs();
		this.parentInstance = execution.getProcessInstance();
		this.parentNodeName = parentNodeName;
	}

	/**
	 * 构造函数，接收流程定义、流程实例对象、执行参数
	 * 
	 * @param process
	 * @param processInstance
	 * @param args
	 */
	public Execution(ProcessEngineConfigurationImpl engineConfiguration, ProcessDefinition processDefinition,
	        ProcessInstance processInstance, Map<String, Object> args)
	{
		if (processDefinition == null || processInstance == null)
		{
			throw new WorkFlowException("构造Execution对象失败，请检查process、instance是否为空");
		}
		this.engineConfiguration = engineConfiguration;
		this.processDefinition = processDefinition;
		this.processInstance = processInstance;
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
	public Execution createSubExecution(Execution execution, ProcessDefinition processDefinition, String parentNodeName)
	{
		return new Execution(execution, processDefinition, parentNodeName);
	}

	/**
	 * 获取流程定义对象
	 * 
	 * @return
	 */
	public ProcessDefinition getProcessDefinition()
	{
		return processDefinition;
	}

	/**
	 * 获取流程模型对象
	 * 
	 * @return
	 */
	public BpmnModel getBpmnModel()
	{
		return processDefinition.getBpmnModel();
	}

	/**
	 * 获取instance
	 * 
	 * @return
	 */
	public ProcessInstance getProcessInstance()
	{
		return processInstance;
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
	public ProcessEngineConfigurationImpl getEngineConfiguration()
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

	/**
	 * 获取childInstanceId
	 * 
	 * @return
	 */
	public String getChildInstanceId()
	{
		return childInstanceId;
	}

	/**
	 * 设置childInstanceId
	 * 
	 * @param childInstanceId
	 */
	public void setChildInstanceId(String childInstanceId)
	{
		this.childInstanceId = childInstanceId;
	}
}
