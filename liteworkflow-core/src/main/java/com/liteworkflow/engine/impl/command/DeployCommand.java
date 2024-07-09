package com.liteworkflow.engine.impl.command;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.model.BpmnModel;
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
	 * @param istream
	 * @param creator
	 */
	public DeployCommand(InputStream istream, String creator)
	{
		try
		{
			this.bytes = IOUtils.toByteArray(istream);
			this.creator = creator;
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

		BpmnModel bpmnModel = engineConfiguration.getCommandExecutor().execute(new BpmnParseCommand(bytes));

		Integer version = genProcessModelVersion(bpmnModel, processDefinitionEntityService);

		ProcessDefinition processDefinition = new ProcessDefinition();
		processDefinition.setId(StringHelper.getPrimaryKey());
		processDefinition.setKey(bpmnModel.getId());
		processDefinition.setName(bpmnModel.getName());
		processDefinition.setCategory(bpmnModel.getCategory());
		processDefinition.setVersion(version);
		processDefinition.setBytes(bytes);
		processDefinition.setCreateTime(LocalDateTime.now());
		processDefinition.setCreator(creator);

		processDefinitionEntityService.addEntity(processDefinition);

		processDefinition.setBpmnModel(bpmnModel);

		return processDefinition;
	}

	/**
	 * 生成流程定义版本号
	 * 
	 * @param bpmnModel
	 * @param processDefinitionEntityService
	 * @return
	 */
	private int genProcessModelVersion(BpmnModel bpmnModel, ProcessDefinitionEntityService processDefinitionEntityService)
	{
		Integer version = processDefinitionEntityService.getLatestVersion(bpmnModel.getId());
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
