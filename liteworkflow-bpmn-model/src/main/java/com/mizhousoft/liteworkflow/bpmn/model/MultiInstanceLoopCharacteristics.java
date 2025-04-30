package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 多实例特征元素
 *
 * @version
 */
public class MultiInstanceLoopCharacteristics extends FlowElement
{
	/**
	 * 集合名称
	 */
	private String collection;

	/**
	 * 元素变量
	 */
	private String elementVariable;

	/**
	 * 是否串行
	 */
	private boolean sequential;

	/**
	 * 完成条件
	 */
	private String completionCondition;

	/**
	 * 获取collection
	 * 
	 * @return
	 */
	public String getCollection()
	{
		return collection;
	}

	/**
	 * 设置collection
	 * 
	 * @param collection
	 */
	public void setCollection(String collection)
	{
		this.collection = collection;
	}

	/**
	 * 获取elementVariable
	 * 
	 * @return
	 */
	public String getElementVariable()
	{
		return elementVariable;
	}

	/**
	 * 设置elementVariable
	 * 
	 * @param elementVariable
	 */
	public void setElementVariable(String elementVariable)
	{
		this.elementVariable = elementVariable;
	}

	/**
	 * 获取sequential
	 * 
	 * @return
	 */
	public boolean isSequential()
	{
		return sequential;
	}

	/**
	 * 设置sequential
	 * 
	 * @param sequential
	 */
	public void setSequential(boolean sequential)
	{
		this.sequential = sequential;
	}

	/**
	 * 获取completionCondition
	 * 
	 * @return
	 */
	public String getCompletionCondition()
	{
		return completionCondition;
	}

	/**
	 * 设置completionCondition
	 * 
	 * @param completionCondition
	 */
	public void setCompletionCondition(String completionCondition)
	{
		this.completionCondition = completionCondition;
	}
}
