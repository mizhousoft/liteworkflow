package com.liteworkflow.engine.impl;

import java.util.List;
import java.util.Map;

import com.liteworkflow.engine.Completion;
import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.ProcessEngineConfiguration;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.JsonHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.persistence.order.entity.CCOrder;
import com.liteworkflow.engine.persistence.order.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.order.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.order.request.ProcessInstPageRequest;
import com.liteworkflow.engine.persistence.order.service.CCOrderEntityService;
import com.liteworkflow.engine.persistence.order.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.order.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.process.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.task.entity.HistoryTask;
import com.liteworkflow.engine.persistence.task.entity.Task;
import com.liteworkflow.engine.persistence.task.service.HistoryTaskEntityService;
import com.liteworkflow.engine.persistence.task.service.TaskEntityService;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程实例业务类
 * 
 * @author yuqs
 * @since 1.0
 */
public class ProcessInstanceServiceImpl extends AccessService implements ProcessInstanceService
{
	private ProcessEngineConfiguration engineConfiguration;

	private ProcessInstanceEntityService processInstanceEntityService;

	private CCOrderEntityService ccOrderEntityService;

	private HistoricProcessInstanceEntityService historicProcessInstanceEntityService;

	private TaskEntityService taskEntityService;

	private HistoryTaskEntityService historyTaskEntityService;

	@Override
	public ProcessInstance getOrder(String orderId)
	{
		return processInstanceEntityService.getOrder(orderId);
	}

	@Override
	public List<ProcessInstance> getActiveOrders(ProcessInstPageRequest request)
	{
		return processInstanceEntityService.queryList(request);
	}

	@Override
	public Page<ProcessInstance> queryPageData(ProcessInstPageRequest request)
	{
		return processInstanceEntityService.queryPageData(request);
	}

	/**
	 * 创建活动实例
	 * 
	 * @see com.liteworkflow.engine.impl.ProcessInstanceServiceImpl#createOrder(ProcessDefinition,
	 *      String, Map, String,
	 *      String)
	 */
	@Override
	public ProcessInstance createOrder(ProcessDefinition process, String operator, Map<String, Object> args)
	{
		return createOrder(process, operator, args, null, null);
	}

	/**
	 * 创建活动实例
	 */
	@Override
	public ProcessInstance createOrder(ProcessDefinition process, String operator, Map<String, Object> args, String parentId,
	        String parentNodeName)
	{
		ProcessInstance order = new ProcessInstance();
		order.setId(StringHelper.getPrimaryKey());
		order.setParentId(parentId);
		order.setParentNodeName(parentNodeName);
		order.setCreateTime(DateHelper.getTime());
		order.setLastUpdateTime(order.getCreateTime());
		order.setCreator(operator);
		order.setLastUpdator(order.getCreator());
		order.setProcessId(process.getId());
		ProcessModel model = process.getModel();
		if (model != null && args != null)
		{
			if (StringHelper.isNotEmpty(model.getExpireTime()))
			{
				String expireTime = DateHelper.parseTime(args.get(model.getExpireTime()));
				order.setExpireTime(expireTime);
			}
			String orderNo = (String) args.get(ProcessEngine.ID);
			if (StringHelper.isNotEmpty(orderNo))
			{
				order.setOrderNo(orderNo);
			}
			else
			{
				order.setOrderNo(model.getGenerator().generate(model));
			}
		}

		order.setVariable(JsonHelper.toJson(args));
		processInstanceEntityService.saveOrderAndHistory(order);
		return order;
	}

	/**
	 * 向活动实例临时添加全局变量数据
	 * 
	 * @param orderId 实例id
	 * @param args 变量数据
	 */
	@Override
	public void addVariable(String orderId, Map<String, Object> args)
	{
		ProcessInstance order = processInstanceEntityService.getOrder(orderId);
		Map<String, Object> data = order.getVariableMap();
		data.putAll(args);
		order.setVariable(JsonHelper.toJson(data));
		processInstanceEntityService.updateOrderVariable(order);
	}

	/**
	 * 创建实例的抄送
	 */
	@Override
	public void createCCOrder(String orderId, String creator, String... actorIds)
	{
		for (String actorId : actorIds)
		{
			CCOrder ccorder = new CCOrder();
			ccorder.setOrderId(orderId);
			ccorder.setActorId(actorId);
			ccorder.setCreator(creator);
			ccorder.setStatus(Constants.STATE_ACTIVE);
			ccorder.setCreateTime(DateHelper.getTime());
			ccOrderEntityService.save(ccorder);
		}
	}

	/**
	 * 更新活动实例的last_Updator、last_Update_Time、expire_Time、version、variable
	 */
	@Override
	public void updateOrder(ProcessInstance order)
	{
		processInstanceEntityService.updateOrder(order);
	}

	/**
	 * 更新抄送记录状态为已阅
	 */
	public void updateCCStatus(String orderId, String... actorIds)
	{
		List<CCOrder> ccorders = ccOrderEntityService.getCCOrder(orderId, actorIds);
		for (CCOrder ccorder : ccorders)
		{
			ccorder.setStatus(Constants.STATE_FINISH);
			ccorder.setFinishTime(DateHelper.getTime());
			ccOrderEntityService.update(ccorder);
		}
	}

	/**
	 * 删除指定的抄送记录
	 */
	public void deleteCCOrder(String orderId, String actorId)
	{
		List<CCOrder> ccorders = ccOrderEntityService.getCCOrder(orderId, actorId);
		for (CCOrder ccorder : ccorders)
		{
			ccOrderEntityService.delete(ccorder);
		}
	}

	/**
	 * 删除活动流程实例数据，更新历史流程实例的状态、结束时间
	 */
	@Override
	public void complete(String orderId)
	{
		ProcessInstance order = processInstanceEntityService.getOrder(orderId);
		HistoricProcessInstance history = historicProcessInstanceEntityService.getHistOrder(orderId);
		history.setOrderState(Constants.STATE_FINISH);
		history.setEndTime(DateHelper.getTime());

		historicProcessInstanceEntityService.update(history);
		processInstanceEntityService.deleteOrder(order);
		Completion completion = getCompletion();
		if (completion != null)
		{
			completion.complete(history);
		}
	}

	/**
	 * 强制中止流程实例
	 * 
	 * @see com.liteworkflow.engine.impl.ProcessInstanceServiceImpl#terminate(String, String)
	 */
	@Override
	public void terminate(String orderId)
	{
		terminate(orderId, null);
	}

	/**
	 * 强制中止活动实例,并强制完成活动任务
	 */
	@Override
	public void terminate(String orderId, String operator)
	{
		List<Task> tasks = taskEntityService.queryByOrderId(orderId);
		for (Task task : tasks)
		{
			engineConfiguration.getTaskService().complete(task.getId(), operator);
		}
		ProcessInstance order = processInstanceEntityService.getOrder(orderId);
		HistoricProcessInstance history = new HistoricProcessInstance(order);
		history.setOrderState(Constants.STATE_TERMINATION);
		history.setEndTime(DateHelper.getTime());

		historicProcessInstanceEntityService.update(history);
		processInstanceEntityService.deleteOrder(order);
		Completion completion = getCompletion();
		if (completion != null)
		{
			completion.complete(history);
		}
	}

	/**
	 * 激活已完成的历史流程实例
	 * 
	 * @param orderId 实例id
	 * @return 活动实例对象
	 */
	@Override
	public ProcessInstance resume(String orderId)
	{
		HistoricProcessInstance historyOrder = historicProcessInstanceEntityService.getHistOrder(orderId);
		ProcessInstance order = historyOrder.undo();
		processInstanceEntityService.saveOrder(order);
		historyOrder.setOrderState(Constants.STATE_ACTIVE);
		historicProcessInstanceEntityService.update(historyOrder);

		List<HistoryTask> histTasks = historyTaskEntityService.queryByOrderId(orderId);
		if (histTasks != null && !histTasks.isEmpty())
		{
			HistoryTask histTask = histTasks.get(0);
			engineConfiguration.getTaskService().resume(histTask.getId(), histTask.getOperator());
		}
		return order;
	}

	/**
	 * 级联删除指定流程实例的所有数据：
	 * 1.wf_order,wf_hist_order
	 * 2.wf_task,wf_hist_task
	 * 3.wf_task_actor,wf_hist_task_actor
	 * 4.wf_cc_order
	 * 
	 * @param id 实例id
	 */
	@Override
	public void cascadeRemove(String id)
	{
		HistoricProcessInstance historyOrder = historicProcessInstanceEntityService.getHistOrder(id);

		List<Task> activeTasks = taskEntityService.queryByOrderId(id);
		List<HistoryTask> historyTasks = historyTaskEntityService.queryByOrderId(id);
		for (Task task : activeTasks)
		{
			taskEntityService.delete(task);
		}
		for (HistoryTask historyTask : historyTasks)
		{
			historyTaskEntityService.deleteHistoryTask(historyTask);
		}
		List<CCOrder> ccOrders = ccOrderEntityService.getCCOrder(id);
		for (CCOrder ccOrder : ccOrders)
		{
			ccOrderEntityService.delete(ccOrder);
		}

		ProcessInstance order = processInstanceEntityService.getOrder(id);
		historicProcessInstanceEntityService.delete(historyOrder);
		if (order != null)
		{
			processInstanceEntityService.deleteOrder(order);
		}
	}

	/**
	 * 设置processInstanceEntityService
	 * 
	 * @param processInstanceEntityService
	 */
	public void setProcessInstanceEntityService(ProcessInstanceEntityService processInstanceEntityService)
	{
		this.processInstanceEntityService = processInstanceEntityService;
	}

	/**
	 * 设置ccOrderEntityService
	 * 
	 * @param ccOrderEntityService
	 */
	public void setCcOrderEntityService(CCOrderEntityService ccOrderEntityService)
	{
		this.ccOrderEntityService = ccOrderEntityService;
	}

	/**
	 * 设置taskEntityService
	 * 
	 * @param taskEntityService
	 */
	public void setTaskEntityService(TaskEntityService taskEntityService)
	{
		this.taskEntityService = taskEntityService;
	}

	/**
	 * 设置historicProcessInstanceEntityService
	 * @param historicProcessInstanceEntityService
	 */
	public void setHistoricProcessInstanceEntityService(HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	/**
	 * 设置historyTaskEntityService
	 * 
	 * @param historyTaskEntityService
	 */
	public void setHistoryTaskEntityService(HistoryTaskEntityService historyTaskEntityService)
	{
		this.historyTaskEntityService = historyTaskEntityService;
	}

	/**
	 * 设置engineConfiguration
	 * @param engineConfiguration
	 */
	public void setEngineConfiguration(ProcessEngineConfiguration engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}
	
}
