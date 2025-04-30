package com.mizhousoft.liteworkflow.boot.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.SmartLifecycle;
import org.springframework.core.io.Resource;

import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.cfg.ProcessEngineConfigurationImpl;
import com.mizhousoft.liteworkflow.engine.domain.DeployOption;

/**
 * 引擎配置
 *
 * @version
 */
public class SpringProcessEngineConfiguration extends ProcessEngineConfigurationImpl implements SmartLifecycle
{
	/**
	 * 要部署的资源
	 */
	private List<Resource> deploymentResources = new ArrayList<>();

	/**
	 * 是否运行
	 */
	protected volatile boolean running = false;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start()
	{
		try
		{
			DeployOption deployOption = new DeployOption();
			deployOption.setDuplicateFilterEnabled(true);

			if (deploymentResources != null && deploymentResources.size() > 0)
			{
				for (Resource deploymentResource : deploymentResources)
				{
					InputStream istream = deploymentResource.getInputStream();

					repositoryService.deploy(istream, "system", deployOption);
				}
			}
		}
		catch (IOException e)
		{
			throw new WorkFlowException("Deploy bmpn failed.", e);
		}

		running = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void stop()
	{
		running = false;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isRunning()
	{
		return running;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getPhase()
	{
		return 100;
	}

	/**
	 * 设置deploymentResources
	 * 
	 * @param deploymentResources
	 */
	public void setDeploymentResources(List<Resource> deploymentResources)
	{
		this.deploymentResources = deploymentResources;
	}
}
