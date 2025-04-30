package com.mizhousoft.liteworkflow.engine.impl.command;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;
import org.springframework.util.Assert;

import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.DeploymentManager;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;

/**
 * 重新部署命令
 *
 * @version
 */
public class RedeployCommand implements Command<ProcessDefEntity>
{
	/**
	 * 数据字节
	 */
	private byte[] bytes;

	/**
	 * 流程定义ID
	 */
	private int processDefinitionId;

	/**
	 * 构造函数
	 *
	 * @param istream
	 * @param processDefinitionId
	 */
	public RedeployCommand(InputStream istream, int processDefinitionId)
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
	 * 构造函数
	 *
	 * @param xmlString
	 * @param processDefinitionId
	 */
	public RedeployCommand(String xmlString, int processDefinitionId)
	{
		this.bytes = xmlString.getBytes(CharEncoding.UTF8);
		this.processDefinitionId = processDefinitionId;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefEntity execute(CommandContext context)
	{
		ProcessEngineConfigurationImpl engineConfiguration = context.getEngineConfiguration();
		ProcessDefEntityService processDefEntityService = engineConfiguration.getProcessDefEntityService();
		DeploymentManager deploymentManager = engineConfiguration.getDeploymentManager();

		ProcessDefEntity processDefinition = processDefEntityService.getById(processDefinitionId);
		Assert.notNull(processDefinition, "Process definition not found, id is " + processDefinitionId);

		BpmnModel bpmnModel = engineConfiguration.getCommandExecutor().execute(new ParseBpmnCommand(bytes));

		processDefinition.setName(bpmnModel.getName());
		processDefinition.setCategory(bpmnModel.getCategory());
		processDefinition.setBytes(bytes);
		processDefinition.setDescription(bpmnModel.getDocumentation());
		processDefinition.setUpdateTime(LocalDateTime.now());

		processDefEntityService.modifyEntity(processDefinition);

		processDefinition.setBpmnModel(bpmnModel);

		deploymentManager.putCache(processDefinition);

		return processDefinition;
	}

}
