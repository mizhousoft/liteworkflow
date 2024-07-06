package com.liteworkflow.engine.impl.command;

import java.util.Set;

import org.springframework.util.Assert;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;

/**
 * 删除流程定义命令
 *
 * @version
 */
public class DeleteDeploymentCommand implements Command<ProcessDefinition>
{
	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;

	/**
	 * 是否级联删除流程、任务、历史等数据
	 */
	private boolean cascade;

	/**
	 * 构造函数
	 *
	 * @param processDefinitionId
	 * @param cascade
	 */
	public DeleteDeploymentCommand(String processDefinitionId, boolean cascade)
	{
		super();
		this.processDefinitionId = processDefinitionId;
		this.cascade = cascade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessDefinitionEntityService processDefinitionEntityService = engineConfiguration.getProcessDefinitionEntityService();
		ProcessInstanceService processInstanceService = engineConfiguration.getProcessInstanceService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();

		ProcessDefinition processDefinition = processDefinitionEntityService.getById(processDefinitionId);
		Assert.notNull(processDefinition, "Process definition not found, id is " + processDefinitionId + '.');

		if (cascade)
		{
			Set<String> instanceIds = historicProcessInstanceEntityService.queryIdsByProcessDefinitionId(processDefinitionId);
			for (String instanceId : instanceIds)
			{
				processInstanceService.deleteInstance(instanceId);
			}
		}

		processDefinitionEntityService.deleteEntity(processDefinition);

		return processDefinition;
	}
}
