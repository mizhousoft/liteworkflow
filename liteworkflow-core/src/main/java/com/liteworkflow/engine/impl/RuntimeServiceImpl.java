package com.liteworkflow.engine.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.RepositoryService;
import com.liteworkflow.engine.RuntimeService;
import com.liteworkflow.engine.helper.AssertHelper;
import com.liteworkflow.engine.model.StartModel;
import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.process.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.workitem.entity.WorkItem;
import com.liteworkflow.engine.persistence.workitem.request.WorkItemPageRequest;
import com.liteworkflow.engine.persistence.workitem.service.WorkItemEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * TODO
 *
 * @version
 */
public class RuntimeServiceImpl implements RuntimeService
{
	private static final Logger log = LoggerFactory.getLogger(RuntimeServiceImpl.class);

	/**
	 * Snaker配置对象
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

	private WorkItemEntityService workItemEntityService;

	/**
	 * 构造函数
	 *
	 * @param configuration
	 */
	public RuntimeServiceImpl(ProcessEngineConfiguration configuration, WorkItemEntityService workItemEntityService)
	{
		super();
		this.configuration = configuration;

		this.repositoryService = configuration.getRepositoryService();
		this.processInstanceService = configuration.getProcessInstanceService();
		this.workItemEntityService = workItemEntityService;
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
			args = new HashMap<String, Object>();
		ProcessDefinition process = repositoryService.getProcessById(id);
		repositoryService.check(process, id);
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
			args = new HashMap<String, Object>();
		ProcessDefinition process = repositoryService.getProcessByVersion(name, version);
		repositoryService.check(process, name);
		return startProcess(process, operator, args);
	}

	private ProcessInstance startProcess(ProcessDefinition process, String operator, Map<String, Object> args)
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
	@Override
	public ProcessInstance startInstanceByExecution(Execution execution)
	{
		ProcessDefinition process = execution.getProcess();
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
	private Execution execute(ProcessDefinition process, String operator, Map<String, Object> args, String parentId, String parentNodeName)
	{
		ProcessInstance order = processInstanceService.createOrder(process, operator, args, parentId, parentNodeName);

		log.debug("创建流程实例对象:" + order);

		Execution current = new Execution(configuration, process, order, args);
		current.setOperator(operator);
		return current;
	}

	@Override
	public Page<WorkItem> getWorkItems(WorkItemPageRequest request)
	{
		return workItemEntityService.queryPageData(request);
	}
}
