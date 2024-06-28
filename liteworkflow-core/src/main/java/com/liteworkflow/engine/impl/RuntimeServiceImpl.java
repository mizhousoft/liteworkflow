package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.impl.executor.ExecutorBuilder;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;

/**
 * TODO
 *
 * @version
 */
public class RuntimeServiceImpl implements RuntimeService
{
	private static final Logger log = LoggerFactory.getLogger(RuntimeServiceImpl.class);

	/**
	 * ProcessEngineConfiguration
	 */
	protected ProcessEngineConfiguration configuration;

	/**
	 * 流程定义业务类
	 */
	protected RepositoryService repositoryService;

	/**
	 * 流程实例业务类
	 */
	protected ProcessInstanceService processInstanceService;

	/**
	 * 构造函数
	 *
	 * @param configuration
	 */
	public RuntimeServiceImpl(ProcessEngineConfiguration configuration)
	{
		super();
		this.configuration = configuration;

		this.repositoryService = configuration.getRepositoryService();
		this.processInstanceService = configuration.getProcessInstanceService();
	}

	/**
	 * 根据流程定义ID启动流程实例
	 */
	@Override
	public ProcessInstance startInstanceById(String id)
	{
		return startInstanceById(id, null, null);
	}

	/**
	 * 根据流程定义ID，操作人ID启动流程实例
	 */
	@Override
	public ProcessInstance startInstanceById(String id, String operator)
	{
		return startInstanceById(id, operator, null);
	}

	/**
	 * 根据流程定义ID，操作人ID，参数列表启动流程实例
	 */
	@Override
	public ProcessInstance startInstanceById(String id, String operator, Map<String, Object> args)
	{
		if (args == null)
		{
			args = new HashMap<String, Object>(0);
		}

		ProcessDefinition process = repositoryService.getById(id);
		Assert.notNull(process, "Process definition not found, id is " + id);

		return startProcess(process, operator, args);
	}

	/**
	 * 根据流程名称启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name)
	{
		return startInstanceByName(name, null, null, null);
	}

	/**
	 * 根据流程名称、版本号启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name, Integer version)
	{
		return startInstanceByName(name, version, null, null);
	}

	/**
	 * 根据流程名称、版本号、操作人启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name, Integer version, String operator)
	{
		return startInstanceByName(name, version, operator, null);
	}

	/**
	 * 根据流程名称、版本号、操作人、参数列表启动流程实例
	 * 
	 * @since 1.3
	 */
	@Override
	public ProcessInstance startInstanceByName(String name, Integer version, String operator, Map<String, Object> args)
	{
		if (args == null)
		{
			args = new HashMap<String, Object>(0);
		}

		ProcessDefinition process = repositoryService.getByVersion(name, version);
		Assert.notNull(process, "Process definition not found, name is " + name);

		return startProcess(process, operator, args);
	}

	private ProcessInstance startProcess(ProcessDefinition process, String operator, Map<String, Object> args)
	{
		Execution execution = execute(process, operator, args, null, null);
		if (process.getModel() != null)
		{
			StartModel start = process.getModel().getStartModel();

			Executor executor = ExecutorBuilder.build(start);
			executor.execute(execution, start);
		}

		return execution.getProcessInstance();
	}

	/**
	 * 根据父执行对象启动子流程实例（用于启动子流程）
	 */
	@Override
	public ProcessInstance startInstanceByExecution(Execution execution)
	{
		ProcessDefinition processDefinition = execution.getProcessDefinition();
		StartModel start = processDefinition.getModel().getStartModel();

		Execution current = execute(processDefinition, execution.getOperator(), execution.getArgs(), execution.getParentInstance().getId(),
		        execution.getParentNodeName());
		Executor executor = ExecutorBuilder.build(start);
		executor.execute(current, start);

		return current.getProcessInstance();
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
	private Execution execute(ProcessDefinition process, String operator, Map<String, Object> args, String parentId, String parentNodeName)
	{
		ProcessInstance instance = processInstanceService.createInstance(process, operator, args, parentId, parentNodeName);

		log.debug("创建流程实例对象:" + instance);

		Execution current = new Execution(configuration, process, instance, args);
		current.setOperator(operator);
		return current;
	}
}
