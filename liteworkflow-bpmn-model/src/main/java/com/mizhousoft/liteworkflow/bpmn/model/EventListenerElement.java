package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 事件监听器元素
 *
 * @version
 */
public class EventListenerElement
{
	/**
	 * 实现类型：类
	 */
	public static final String IMPLEMENTATION_TYPE_CLASS = "class";

	/**
	 * 实现类型：委托表达式
	 */
	public static final String IMPLEMENTATION_TYPE_DELEGATEEXPRESSION = "delegateExpression";

	/**
	 * 事件类型
	 */
	private String event;

	/**
	 * 事件实现类型
	 */
	private String implementationType;

	/**
	 * 事件实现
	 */
	private String implementation;

	/**
	 * 获取event
	 * 
	 * @return
	 */
	public String getEvent()
	{
		return event;
	}

	/**
	 * 设置event
	 * 
	 * @param event
	 */
	public void setEvent(String event)
	{
		this.event = event;
	}

	/**
	 * 获取implementationType
	 * 
	 * @return
	 */
	public String getImplementationType()
	{
		return implementationType;
	}

	/**
	 * 设置implementationType
	 * 
	 * @param implementationType
	 */
	public void setImplementationType(String implementationType)
	{
		this.implementationType = implementationType;
	}

	/**
	 * 获取implementation
	 * 
	 * @return
	 */
	public String getImplementation()
	{
		return implementation;
	}

	/**
	 * 设置implementation
	 * 
	 * @param implementation
	 */
	public void setImplementation(String implementation)
	{
		this.implementation = implementation;
	}
}
