package com.liteworkflow.engine.parser;

import org.w3c.dom.Element;

import com.liteworkflow.engine.model.NodeModel;

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
	public static final String NODE_TRANSITION = "transition";

	/**
	 * 扩展元素节点名称
	 */
	public static final String NODE_EXTENSION_ELEMENTS = "extensionElements";

	/**
	 * 执行监听器节点名称
	 */
	public static final String NODE_EXECUTION_LISTENER = "executionListener";

	/**
	 * 任务监听器节点名称
	 */
	public static final String NODE_TASK_LISTENER = "taskListener";

	/**
	 * 节点属性名称
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * 节点显示名称
	 */
	public static final String ATTR_DISPLAYNAME = "displayName";

	/**
	 * 分类属性名称
	 */
	public static final String ATTR_CATEGORY = "category";

	/**
	 * 流程实例URL
	 */
	public static final String ATTR_INSTANCEURL = "instanceUrl";

	/**
	 * 决策表达式
	 */
	public static final String ATTR_EXPR = "expr";

	/**
	 * 处理类
	 */
	public static final String ATTR_HANDLECLASS = "handleClass";

	/**
	 * 
	 */
	public static final String ATTR_ASSIGNEE = "assignee";

	/**
	 * 
	 */
	public static final String ATTR_PERFORMTYPE = "performType";

	/**
	 * 
	 */
	public static final String ATTR_TO = "to";

	/**
	 * 
	 */
	public static final String ATTR_PROCESSNAME = "processName";

	/**
	 * 
	 */
	public static final String ATTR_VERSION = "version";

	/**
	 * 
	 */
	public static final String ATTR_EXPIRETIME = "expireTime";

	/**
	 * 
	 */
	public static final String ATTR_AUTOEXECUTE = "autoExecute";

	/**
	 * 
	 */
	public static final String ATTR_CALLBACK = "callback";

	/**
	 * 
	 */
	public static final String ATTR_REMINDERTIME = "reminderTime";

	/**
	 * 
	 */
	public static final String ATTR_REMINDERREPEAT = "reminderRepeat";

	/**
	 * 
	 */
	public static final String ATTR_CLAZZ = "clazz";

	/**
	 * 
	 */
	public static final String ATTR_METHODNAME = "methodName";

	/**
	 * 
	 */
	public static final String ATTR_EVENT = "event";

	/**
	 * 
	 */
	public static final String ATTR_CLASS = "class";

	/**
	 * 
	 */
	public static final String ATTR_DELEGATE_EXPRESSION = "delegateExpression";

	/**
	 * 
	 */
	public static final String ATTR_ARGS = "args";

	/**
	 * 
	 */
	public static final String ATTR_VAR = "var";

	/**
	 * 
	 */
	public static final String ATTR_LAYOUT = "layout";

	/**
	 * 
	 */
	public static final String ATTR_G = "g";

	/**
	 * 
	 */
	public static final String ATTR_OFFSET = "offset";

	/**
	 * 
	 */
	public static final String ATTR_PREINTERCEPTORS = "preInterceptors";

	/**
	 * 
	 */
	public static final String ATTR_POSTINTERCEPTORS = "postInterceptors";

	/**
	 * 解析成功后，提供返回NodeModel模型对象
	 * 
	 * @return 节点模型
	 */
	public NodeModel getModel();

	/**
	 * 获取节点名称
	 * 
	 * @return
	 */
	public String getNodeName();

	/**
	 * 节点dom元素解析方法，由实现类完成解析
	 * 
	 * @param element dom元素
	 */
	public void parse(Element element);

	/**
	 * 校验节点模型设计是否正确
	 * 
	 */
	public void checkModel();
}
