package com.liteworkflow.engine.impl.command;

import java.util.List;

import com.liteworkflow.engine.ProcessInstanceService;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.persistence.entity.HistoricProcessInstance;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.HistoricInstancePageRequest;
import com.liteworkflow.engine.persistence.service.HistoricProcessInstanceEntityService;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;

/**
 * 删除命令命令
 *
 * @version
 */
public class DeleteDeploymentCommand implements Command<ProcessDefinition>
{
	private String id;

	private boolean cascade;

	/**
	 * 构造函数
	 *
	 * @param id
	 * @param cascade
	 */
	public DeleteDeploymentCommand(String id, boolean cascade)
	{
		super();
		this.id = id;
		this.cascade = cascade;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getProcessEngineConfiguration();
		ProcessDefinitionEntityService processDefinitionEntityService = engineConfiguration.getProcessDefinitionEntityService();
		HistoricProcessInstanceEntityService historicProcessInstanceEntityService = engineConfiguration
		        .getHistoricProcessInstanceEntityService();

		ProcessDefinition processDefinition = processDefinitionEntityService.getById(id);

		HistoricInstancePageRequest request = new HistoricInstancePageRequest();
		request.setProcessDefinitionId(id);
		List<HistoricProcessInstance> historicInstances = historicProcessInstanceEntityService.queryList(request);

		ProcessInstanceService processInstanceService = engineConfiguration.getProcessInstanceService();

		for (HistoricProcessInstance historicInstance : historicInstances)
		{
			processInstanceService.cascadeRemove(historicInstance.getId());
		}

		processDefinitionEntityService.deleteEntity(processDefinition);

		return processDefinition;
	}
}
