package com.mizhousoft.liteworkflow.engine.domain;

/**
 * 部署选项
 *
 * @version
 */
public class DeployOption
{
	/**
	 * 重复过滤启用
	 */
	private boolean duplicateFilterEnabled;

	/**
	 * 获取duplicateFilterEnabled
	 * 
	 * @return
	 */
	public boolean isDuplicateFilterEnabled()
	{
		return duplicateFilterEnabled;
	}

	/**
	 * 设置duplicateFilterEnabled
	 * 
	 * @param duplicateFilterEnabled
	 */
	public void setDuplicateFilterEnabled(boolean duplicateFilterEnabled)
	{
		this.duplicateFilterEnabled = duplicateFilterEnabled;
	}
}
