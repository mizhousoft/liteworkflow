package com.liteworkflow.engine.impl.command;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.parser.ModelParser;
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
	private String id;

	/**
	 * 构造函数
	 *
	 * @param input
	 * @param id
	 */
	public RedeployCommand(InputStream input, String id)
	{
		try
		{
			this.bytes = IOUtils.toByteArray(input);
			this.id = id;
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

		ProcessDefinition processDefinition = processDefinitionEntityService.getById(id);
		Assert.notNull(processDefinition, "Process definition not found, id is " + id);

		ProcessModel processModel = ModelParser.parse(bytes);

		processDefinition.setName(processModel.getName());
		processDefinition.setDisplayName(processModel.getDisplayName());
		processDefinition.setCategory(processModel.getCategory());
		processDefinition.setBytes(bytes);
		processDefinition.setInstanceUrl(processModel.getInstanceUrl());

		processDefinitionEntityService.modifyEntity(processDefinition);

		processDefinition.setModel(processModel);

		return processDefinition;
	}

}
