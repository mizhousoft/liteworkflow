package com.liteworkflow.engine.impl.parser;

import org.w3c.dom.Element;

import com.liteworkflow.engine.model.FlowElement;

/**
 * 节点解析接口
 * 
 * @version
 */
public interface NodeParser
{
	/**
	 * 变迁节点名称
	 */
	String NODE_TRANSITION = "transition";

	/**
	 * 扩展元素节点名称
	 */
	String NODE_EXTENSION_ELEMENTS = "extensionElements";

	/**
	 * 执行监听器节点名称
	 */
	String NODE_EXECUTION_LISTENER = "executionListener";

	/**
	 * 任务监听器节点名称
	 */
	String NODE_TASK_LISTENER = "taskListener";

	/**
	 * 节点属性ID
	 */
	String ATTR_ID = "id";

	/**
	 * 节点名称
	 */
	String ATTR_NAME = "name";

	/**
	 * 分类属性名称
	 */
	String ATTR_CATEGORY = "category";

	/**
	 * 决策表达式
	 */
	String ATTR_EXPR = "expr";

	/**
	 * 处理类
	 */
	String ATTR_HANDLECLASS = "handleClass";

	/**
	 * 
	 */
	String ATTR_ASSIGNEE = "assignee";

	/**
	 * 
	 */
	String ATTR_PERFORMTYPE = "performType";

	/**
	 * 
	 */
	String ATTR_TO = "to";

	/**
	 * 
	 */
	String ATTR_VERSION = "version";

	/**
	 * 
	 */
	String ATTR_EXPIRETIME = "expireTime";

	/**
	 * 
	 */
	String ATTR_AUTOEXECUTE = "autoExecute";

	/**
	 * 
	 */
	String ATTR_REMINDERTIME = "reminderTime";

	/**
	 * 
	 */
	String ATTR_REMINDERREPEAT = "reminderRepeat";

	/**
	 * 
	 */
	String ATTR_EVENT = "event";

	/**
	 * 
	 */
	String ATTR_CLASS = "class";

	/**
	 * 
	 */
	String ATTR_DELEGATE_EXPRESSION = "delegateExpression";

	/**
	 * 
	 */
	String ATTR_LAYOUT = "layout";

	/**
	 * 
	 */
	String ATTR_G = "g";

	/**
	 * 
	 */
	String ATTR_OFFSET = "offset";

	/**
	 * 解析成功后，提供返回NodeModel模型对象
	 * 
	 * @return 节点模型
	 */
	FlowElement getModel();

	/**
	 * 获取元素名称
	 * 
	 * @return
	 */
	String getElementName();

	/**
	 * 节点dom元素解析方法，由实现类完成解析
	 * 
	 * @param element dom元素
	 */
	void parse(Element element);

	/**
	 * 校验节点模型设计是否正确
	 * 
	 */
	void checkModel();
}
