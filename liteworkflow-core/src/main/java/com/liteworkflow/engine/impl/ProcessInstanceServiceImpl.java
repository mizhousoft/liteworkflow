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
import com.liteworkflow.engine.persistence.entity.CCOrder;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.HistoricTask;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.entity.ProcessInstance;
import com.liteworkflow.engine.persistence.entity.Task;
import com.liteworkflow.engine.persistence.request.ProcessInstPageRequest;
import com.liteworkflow.engine.persistence.service.CCOrderEntityService;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.HistoricTaskEntityService;
import com.liteworkflow.engine.persistence.service.ProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.TaskEntityService;
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

	private HistoricTaskEntityService historicTaskEntityService;

	@Override
	public ProcessInstance getInstance(String instanceId)
	{
		return processInstanceEntityService.getInstance(instanceId);
	}

	@Override
	public List<ProcessInstance> getActiveInstances(ProcessInstPageRequest request)
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
	 * @see com.liteworkflow.engine.impl.ProcessInstanceServiceImpl#createInstance(ProcessDefinition,
	 *      String, Map, String,
	 *      String)
	 */
	@Override
	public ProcessInstance createInstance(ProcessDefinition process, String operator, Map<String, Object> args)
	{
		return createInstance(process, operator, args, null, null);
	}

	/**
	 * 创建活动实例
	 */
	@Override
	public ProcessInstance createInstance(ProcessDefinition process, String operator, Map<String, Object> args, String parentId,
	        String parentNodeName)
	{
		ProcessInstance instance = new ProcessInstance();
		instance.setId(StringHelper.getPrimaryKey());
		instance.setParentId(parentId);
		instance.setParentNodeName(parentNodeName);
		instance.setCreateTime(DateHelper.getTime());
		instance.setLastUpdateTime(instance.getCreateTime());
		instance.setCreator(operator);
		instance.setLastUpdator(instance.getCreator());
		instance.setProcessId(process.getId());
		ProcessModel model = process.getModel();
		if (model != null && args != null)
		{
			if (StringHelper.isNotEmpty(model.getExpireTime()))
			{
				String expireTime = DateHelper.parseTime(args.get(model.getExpireTime()));
				instance.setExpireTime(expireTime);
			}
			String orderNo = (String) args.get(ProcessEngine.ID);
			if (StringHelper.isNotEmpty(orderNo))
			{
				instance.setOrderNo(orderNo);
			}
			else
			{
				instance.setOrderNo(model.getGenerator().generate(model));
			}
		}

		instance.setVariable(JsonHelper.toJson(args));
		processInstanceEntityService.saveInstanceAndHistoric(instance);
		return instance;
	}

	/**
	 * 向活动实例临时添加全局变量数据
	 * 
	 * @param instanceId 实例id
	 * @param args 变量数据
	 */
	@Override
	public void addVariable(String instanceId, Map<String, Object> args)
	{
		ProcessInstance instance = processInstanceEntityService.getInstance(instanceId);
		Map<String, Object> data = instance.getVariableMap();
		data.putAll(args);
		instance.setVariable(JsonHelper.toJson(data));
		processInstanceEntityService.updateVariable(instance);
	}

	/**
	 * 创建实例的抄送
	 */
	@Override
	public void createCCOrder(String instanceId, String creator, String... actorIds)
	{
		for (String actorId : actorIds)
		{
			CCOrder ccorder = new CCOrder();
			ccorder.setInstanceId(instanceId);
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
	public void updateInstance(ProcessInstance instance)
	{
		processInstanceEntityService.updateInstance(instance);
	}

	/**
	 * 更新抄送记录状态为已阅
	 */
	public void updateCCStatus(String instanceId, String... actorIds)
	{
		List<CCOrder> ccorders = ccOrderEntityService.getCCOrder(instanceId, actorIds);
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
	public void deleteCCOrder(String instanceId, String actorId)
	{
		List<CCOrder> ccorders = ccOrderEntityService.getCCOrder(instanceId, actorId);
		for (CCOrder ccorder : ccorders)
		{
			ccOrderEntityService.delete(ccorder);
		}
	}

	/**
	 * 删除活动流程实例数据，更新历史流程实例的状态、结束时间
	 */
	@Override
	public void complete(String instanceId)
	{
		ProcessInstance instance = processInstanceEntityService.getInstance(instanceId);
		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getHistoricInstance(instanceId);
		historicInstance.setOrderState(Constants.STATE_FINISH);
		historicInstance.setEndTime(DateHelper.getTime());

		historicProcessInstanceEntityService.update(historicInstance);
		processInstanceEntityService.deleteInstance(instance);
		Completion completion = getCompletion();
		if (completion != null)
		{
			completion.complete(historicInstance);
		}
	}

	/**
	 * 强制中止流程实例
	 * 
	 * @see com.liteworkflow.engine.impl.ProcessInstanceServiceImpl#terminate(String, String)
	 */
	@Override
	public void terminate(String instanceId)
	{
		terminate(instanceId, null);
	}

	/**
	 * 强制中止活动实例,并强制完成活动任务
	 */
	@Override
	public void terminate(String instanceId, String operator)
	{
		List<Task> tasks = taskEntityService.queryByInstanceId(instanceId);
		for (Task task : tasks)
		{
			engineConfiguration.getTaskService().complete(task.getId(), operator);
		}
		ProcessInstance order = processInstanceEntityService.getInstance(instanceId);
		HistoricProcessInstance historicInstance = new HistoricProcessInstance(order);
		historicInstance.setOrderState(Constants.STATE_TERMINATION);
		historicInstance.setEndTime(DateHelper.getTime());

		historicProcessInstanceEntityService.update(historicInstance);
		processInstanceEntityService.deleteInstance(order);
		Completion completion = getCompletion();
		if (completion != null)
		{
			completion.complete(historicInstance);
		}
	}

	/**
	 * 激活已完成的历史流程实例
	 * 
	 * @param instanceId 实例id
	 * @return 活动实例对象
	 */
	@Override
	public ProcessInstance resume(String instanceId)
	{
		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getHistoricInstance(instanceId);
		ProcessInstance order = historicInstance.undo();
		processInstanceEntityService.saveInstance(order);
		historicInstance.setOrderState(Constants.STATE_ACTIVE);
		historicProcessInstanceEntityService.update(historicInstance);

		List<HistoricTask> histTasks = historicTaskEntityService.queryByInstanceId(instanceId);
		if (histTasks != null && !histTasks.isEmpty())
		{
			HistoricTask histTask = histTasks.get(0);
			engineConfiguration.getTaskService().resume(histTask.getId(), histTask.getOperator());
		}
		return order;
	}

	/**
	 * 级联删除指定流程实例的所有数据：
	 * 1.wf_process_instance,wf_hist_process_instance
	 * 2.wf_task,wf_hist_task
	 * 3.wf_task_actor,wf_hist_task_actor
	 * 4.wf_cc_order
	 * 
	 * @param id 实例id
	 */
	@Override
	public void cascadeRemove(String id)
	{
		HistoricProcessInstance historicInstance = historicProcessInstanceEntityService.getHistoricInstance(id);

		List<Task> activeTasks = taskEntityService.queryByInstanceId(id);
		List<HistoricTask> historicTasks = historicTaskEntityService.queryByInstanceId(id);
		for (Task task : activeTasks)
		{
			taskEntityService.delete(task);
		}
		for (HistoricTask historicTask : historicTasks)
		{
			historicTaskEntityService.deleteEntity(historicTask);
		}
		List<CCOrder> ccOrders = ccOrderEntityService.getCCOrder(id);
		for (CCOrder ccOrder : ccOrders)
		{
			ccOrderEntityService.delete(ccOrder);
		}

		ProcessInstance instance = processInstanceEntityService.getInstance(id);
		historicProcessInstanceEntityService.delete(historicInstance);
		if (instance != null)
		{
			processInstanceEntityService.deleteInstance(instance);
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
	 * 
	 * @param historicProcessInstanceEntityService
	 */
	public void setHistoricProcessInstanceEntityService(HistoricProcessInstanceEntityService historicProcessInstanceEntityService)
	{
		this.historicProcessInstanceEntityService = historicProcessInstanceEntityService;
	}

	/**
	 * 设置historicTaskEntityService
	 * 
	 * @param historicTaskEntityService
	 */
	public void setHistoricTaskEntityService(HistoricTaskEntityService historicTaskEntityService)
	{
		this.historicTaskEntityService = historicTaskEntityService;
	}

	/**
	 * 设置engineConfiguration
	 * 
	 * @param engineConfiguration
	 */
	public void setEngineConfiguration(ProcessEngineConfiguration engineConfiguration)
	{
		this.engineConfiguration = engineConfiguration;
	}

}
