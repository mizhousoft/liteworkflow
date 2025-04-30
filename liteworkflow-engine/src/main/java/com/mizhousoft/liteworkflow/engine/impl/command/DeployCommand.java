package com.mizhousoft.liteworkflow.engine.impl.command;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.DeployOption;
import com.mizhousoft.liteworkflow.engine.impl.Command;
import com.mizhousoft.liteworkflow.engine.impl.CommandContext;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.DeploymentManager;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;

/**
 * 部署命令
 *
 * @version
 */
public class DeployCommand implements Command<ProcessDefEntity>
{
	private static final Logger LOG = LoggerFactory.getLogger(DeployCommand.class);

	/**
	 * 数据字节
	 */
	private byte[] bytes;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 部署选项
	 */
	private DeployOption deployOption;

	/**
	 * 构造函数
	 *
	 * @param istream
	 * @param creator
	 * @param deployOption
	 */
	public DeployCommand(InputStream istream, String creator, DeployOption deployOption)
	{
		try
		{
			this.bytes = IOUtils.toByteArray(istream);
			this.creator = creator;
			this.deployOption = deployOption;
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
	 * @param creator
	 * @param deployOption
	 */
	public DeployCommand(String xmlString, String creator, DeployOption deployOption)
	{
		this.bytes = xmlString.getBytes(CharEncoding.UTF8);
		this.creator = creator;
		this.deployOption = deployOption;
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

		BpmnModel bpmnModel = engineConfiguration.getCommandExecutor().execute(new ParseBpmnCommand(bytes));

		if (deployOption.isDuplicateFilterEnabled())
		{
			Integer version = processDefEntityService.getLatestVersion(bpmnModel.getId());
			if (null != version)
			{
				ProcessDefEntity processDefinition = deploymentManager.getByVersion(bpmnModel.getId(), version);
				if (null != processDefinition)
				{
					LOG.info("The BpmnModel {} has been deployed, skip deployment.", bpmnModel.getId());

					return processDefinition;
				}
			}
		}

		Integer version = genProcessModelVersion(bpmnModel, processDefEntityService);

		ProcessDefEntity processDefinition = new ProcessDefEntity();
		processDefinition.setKey(bpmnModel.getId());
		processDefinition.setName(bpmnModel.getName());
		processDefinition.setCategory(bpmnModel.getCategory());
		processDefinition.setVersion(version);
		processDefinition.setBytes(bytes);
		processDefinition.setDescription(bpmnModel.getDocumentation());
		processDefinition.setCreateTime(LocalDateTime.now());
		processDefinition.setUpdateTime(processDefinition.getCreateTime());
		processDefinition.setCreator(creator);

		processDefEntityService.addEntity(processDefinition);

		processDefinition.setBpmnModel(bpmnModel);

		deploymentManager.putCache(processDefinition);

		return processDefinition;
	}

	/**
	 * 生成流程定义版本号
	 * 
	 * @param bpmnModel
	 * @param processDefEntityService
	 * @return
	 */
	private int genProcessModelVersion(BpmnModel bpmnModel, ProcessDefEntityService processDefEntityService)
	{
		Integer version = processDefEntityService.getLatestVersion(bpmnModel.getId());
		if (version == null)
		{
			version = 1;
		}
		else
		{
			version = version + 1;
		}

		return version;
	}
}
