package com.liteworkflow.engine.impl.command;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;

/**
 * 重新部署命令
 *
 * @version
 */
public class RedeployCommand implements Command<ProcessDefinition>
{
	/**
	 * 数据字节
	 */
	private byte[] bytes;

	/**
	 * 流程定义ID
	 */
	private String processDefinitionId;

	/**
	 * 构造函数
	 *
	 * @param istream
	 * @param processDefinitionId
	 */
	public RedeployCommand(InputStream istream, String processDefinitionId)
	{
		try
		{
			this.bytes = IOUtils.toByteArray(istream);
			this.processDefinitionId = processDefinitionId;
		}
		catch (IOException e)
		{
			throw new WorkFlowException("Read input stream failed.", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessDefinitionEntityService processDefinitionEntityService = engineConfiguration.getProcessDefinitionEntityService();

		ProcessDefinition processDefinition = processDefinitionEntityService.getById(processDefinitionId);
		Assert.notNull(processDefinition, "Process definition not found, id is " + processDefinitionId);

		BpmnModel bpmnModel = engineConfiguration.getCommandExecutor().execute(new BpmnParseCommand(bytes));

		processDefinition.setName(bpmnModel.getName());
		processDefinition.setCategory(bpmnModel.getCategory());
		processDefinition.setBytes(bytes);

		processDefinitionEntityService.modifyEntity(processDefinition);

		processDefinition.setBpmnModel(bpmnModel);

		return processDefinition;
	}

}
