package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser;

import org.w3c.dom.Element;

import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;

/**
 * 元素解析接口
 * 
 * @version
 */
public abstract class ElementParseHandler
{
	/**
	 * 是否支持处理
	 * 
	 * @param elementName
	 * @return
	 */
	public abstract boolean isSupported(String elementName);

	/**
	 * 节点dom元素解析方法，由实现类完成解析
	 * 
	 * @param element dom元素
	 */
	public abstract FlowElement parse(Element element);
}
