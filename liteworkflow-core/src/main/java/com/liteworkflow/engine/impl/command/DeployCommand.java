package com.liteworkflow.engine.impl.command;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.parser.ModelParser;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.service.ProcessDefinitionEntityService;

/**
 * 部署命令
 *
 * @version
 */
public class DeployCommand implements Command<ProcessDefinition>
{
	/**
	 * 数据字节
	 */
	private byte[] bytes;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 构造函数
	 *
	 * @param input
	 * @param creator
	 */
	public DeployCommand(InputStream input, String creator)
	{
		try
		{
			this.bytes = IOUtils.toByteArray(input);
			this.creator = creator;
		}
		catch (IOException e)
		{
			throw new ProcessException("Read input stream failed.", e);
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

		ProcessModel processModel = ModelParser.parse(bytes);

		Integer version = genProcessModelVersion(processModel, processDefinitionEntityService);

		ProcessDefinition processDefinition = new ProcessDefinition();
		processDefinition.setId(StringHelper.getPrimaryKey());
		processDefinition.setName(processModel.getName());
		processDefinition.setDisplayName(processModel.getDisplayName());
		processDefinition.setCategory(processModel.getCategory());
		processDefinition.setVersion(version);
		processDefinition.setBytes(bytes);
		processDefinition.setInstanceUrl(processModel.getInstanceUrl());
		processDefinition.setCreateTime(LocalDateTime.now());
		processDefinition.setCreator(creator);

		processDefinitionEntityService.addEntity(processDefinition);

		processDefinition.setModel(processModel);

		return processDefinition;
	}

	/**
	 * 生成流程定义版本号
	 * 
	 * @param processModel
	 * @param processDefinitionEntityService
	 * @return
	 */
	private int genProcessModelVersion(ProcessModel processModel, ProcessDefinitionEntityService processDefinitionEntityService)
	{
		Integer version = processDefinitionEntityService.getLatestVersion(processModel.getName());
		if (version == null)
		{
			version = 0;
		}
		else
		{
			version = version + 1;
		}

		return version;
	}
}
