package com.mizhousoft.liteworkflow.boot.configuration;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置属性
 *
 * @version
 */
@Component
@ConfigurationProperties(prefix = "workflow")
public class FlowProperties
{
	/**
	 * 判断是否自动化部署
	 */
	private boolean checkProcessDefinitions = true;

	/**
	 * 部署目录前缀
	 */
	private String processDefinitionLocationPrefix = "classpath*:/processes/";

	/**
	 * 部署目录后缀
	 */
	private List<String> processDefinitionLocationSuffixes = Arrays.asList("**.bpmn20.xml");

	/**
	 * 获取checkProcessDefinitions
	 * 
	 * @return
	 */
	public boolean isCheckProcessDefinitions()
	{
		return checkProcessDefinitions;
	}

	/**
	 * 设置checkProcessDefinitions
	 * 
	 * @param checkProcessDefinitions
	 */
	public void setCheckProcessDefinitions(boolean checkProcessDefinitions)
	{
		this.checkProcessDefinitions = checkProcessDefinitions;
	}

	/**
	 * 获取processDefinitionLocationPrefix
	 * 
	 * @return
	 */
	public String getProcessDefinitionLocationPrefix()
	{
		return processDefinitionLocationPrefix;
	}

	/**
	 * 设置processDefinitionLocationPrefix
	 * 
	 * @param processDefinitionLocationPrefix
	 */
	public void setProcessDefinitionLocationPrefix(String processDefinitionLocationPrefix)
	{
		this.processDefinitionLocationPrefix = processDefinitionLocationPrefix;
	}

	/**
	 * 获取processDefinitionLocationSuffixes
	 * 
	 * @return
	 */
	public List<String> getProcessDefinitionLocationSuffixes()
	{
		return processDefinitionLocationSuffixes;
	}

	/**
	 * 设置processDefinitionLocationSuffixes
	 * 
	 * @param processDefinitionLocationSuffixes
	 */
	public void setProcessDefinitionLocationSuffixes(List<String> processDefinitionLocationSuffixes)
	{
		this.processDefinitionLocationSuffixes = processDefinitionLocationSuffixes;
	}
}
