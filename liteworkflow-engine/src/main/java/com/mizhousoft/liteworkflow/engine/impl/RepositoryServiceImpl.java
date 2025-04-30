package com.mizhousoft.liteworkflow.engine.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.commons.data.util.PageBuilder;
import com.mizhousoft.commons.lang.CharEncoding;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.RepositoryService;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.DeployOption;
import com.mizhousoft.liteworkflow.engine.domain.ProcessDefinition;
import com.mizhousoft.liteworkflow.engine.impl.command.DeleteDeploymentCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.DeployCommand;
import com.mizhousoft.liteworkflow.engine.impl.command.RedeployCommand;
import com.mizhousoft.liteworkflow.engine.impl.persistence.entity.ProcessDefEntity;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.DeploymentManager;
import com.mizhousoft.liteworkflow.engine.impl.persistence.service.ProcessDefEntityService;
import com.mizhousoft.liteworkflow.engine.request.ProcessDefPageRequest;

/**
 * 流程定义业务类
 * 
 * @version
 */
public class RepositoryServiceImpl extends CommonServiceImpl implements RepositoryService
{
	/**
	 * ProcessDefEntityService
	 */
	private ProcessDefEntityService processDefEntityService;

	/**
	 * DeploymentManager
	 */
	private DeploymentManager deploymentManager;

	/**
	 * 构造函数
	 *
	 * @param engineConfiguration
	 * @param deploymentManager
	 * @param processDefEntityService
	 */
	public RepositoryServiceImpl(ProcessEngineConfigurationImpl engineConfiguration, DeploymentManager deploymentManager,
	        ProcessDefEntityService processDefEntityService)
	{
		super(engineConfiguration);

		this.deploymentManager = deploymentManager;
		this.processDefEntityService = processDefEntityService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition deploy(InputStream istream, String creator, DeployOption deployOption)
	{
		ProcessDefEntity processDefinition = commandExecutor.execute(new DeployCommand(istream, creator, deployOption));

		return processDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition deploy(String xmlString, String creator, DeployOption deployOption)
	{
		ProcessDefEntity processDefinition = commandExecutor.execute(new DeployCommand(xmlString, creator, deployOption));

		return processDefinition;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition redeploy(int processDefinitionId, InputStream istream)
	{
		return commandExecutor.execute(new RedeployCommand(istream, processDefinitionId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition redeploy(int processDefinitionId, String xmlString)
	{
		return commandExecutor.execute(new RedeployCommand(xmlString, processDefinitionId));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteDeployment(int processDefinitionId, boolean cascade)
	{
		commandExecutor.execute(new DeleteDeploymentCommand(processDefinitionId, cascade));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BpmnModel getBpmnModel(int processDefinitionId)
	{
		ProcessDefEntity processDefinition = deploymentManager.getProcessDefinition(processDefinitionId);
		if (null != processDefinition)
		{
			return processDefinition.getBpmnModel();
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getBpmnXMLString(int processDefinitionId)
	{
		ProcessDefEntity processDefinition = deploymentManager.getProcessDefinition(processDefinitionId);
		if (null != processDefinition)
		{
			return new String(processDefinition.getBytes(), CharEncoding.UTF8);
		}

		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getProcessDefinition(int processDefinitionId)
	{
		return deploymentManager.getProcessDefinition(processDefinitionId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getLatestByKey(String processDefinitionKey)
	{
		return deploymentManager.getLatestByKey(processDefinitionKey);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ProcessDefinition getByVersion(String processDefinitionKey, Integer version)
	{
		return deploymentManager.getByVersion(processDefinitionKey, version);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefinition> queryList(String processDefinitionKey)
	{
		List<ProcessDefEntity> list = processDefEntityService.queryByKey(processDefinitionKey);

		return new ArrayList<>(list);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Page<ProcessDefinition> queryPageData(ProcessDefPageRequest request)
	{
		Page<ProcessDefEntity> page = processDefEntityService.queryPageData(request);

		return PageBuilder.build(new ArrayList<>(page.getContent()), request, page.getTotalNumber());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ProcessDefinition> queryLatestList()
	{
		List<ProcessDefEntity> list = processDefEntityService.queryLatestList();

		return new ArrayList<>(list);
	}
}
