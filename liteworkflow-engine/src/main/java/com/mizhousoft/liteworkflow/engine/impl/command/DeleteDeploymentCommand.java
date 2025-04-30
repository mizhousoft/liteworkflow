package com.mizhousoft.liteworkflow.engine.impl.command;

import java.util.Set;

import org.springframework.util.Assert;

import com.mizhousoft.liteworkflow.engine.RuntimeService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.DeploymentManager;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.HistoricProcessInstanceEntityService;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;

/**
 * 删除流程定义命令
 *
 * @version
 */
public class DeleteDeploymentCommand implements Command<ProcessDefEntity>
{
	/**
	 * 流程定义ID
	 */
	private int processDefinitionId;

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
	public DeleteDeploymentCommand(int processDefinitionId, boolean cascade)
	{
		super();
		this.processDefinitionId = processDefinitionId;
		this.cascade = cascade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefEntity execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessDefEntityService processDefEntityService = engineConfiguration.getProcessDefEntityService();
		RuntimeService runtimeService = engineConfiguration.getRuntimeService();
		HistoricProcessInstanceEntityService historicProcessInstanceService = engineConfiguration.getHistoricProcessInstanceEntityService();
		DeploymentManager deploymentManager = engineConfiguration.getDeploymentManager();

		ProcessDefEntity processDefinition = processDefEntityService.getById(processDefinitionId);
		Assert.notNull(processDefinition, "Process definition not found, id is " + processDefinitionId + '.');

		if (cascade)
		{
			Set<Integer> instanceIds = historicProcessInstanceService.queryIdsByProcessDefinitionId(processDefinitionId);
			for (Integer instanceId : instanceIds)
			{
				runtimeService.deleteInstance(instanceId);
			}
		}

		processDefEntityService.deleteEntity(processDefinition);
		deploymentManager.removeCache(processDefinition);

		return processDefinition;
	}
}
