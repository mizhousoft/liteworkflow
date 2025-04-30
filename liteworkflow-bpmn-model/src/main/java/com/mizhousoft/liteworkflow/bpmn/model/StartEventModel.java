package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 开始节点定义start元素
 * 
 * @version
 */
public class StartEventModel extends EventModel
{
	/**
	 * 发起人
	 */
	private String initiator;

	/**
	 * 获取initiator
	 * 
	 * @return
	 */
	public String getInitiator()
	{
		return initiator;
	}

	/**
	 * 设置initiator
	 * 
	 * @param initiator
	 */
	public void setInitiator(String initiator)
	{
		this.initiator = initiator;
	}
}
