package com.liteworkflow.engine.impl.bpmn.parser;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.model.FlowElement;

/**
 * 元素解析接口
 * 
 * @version
 */
public abstract class ElementParseHandler
{
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
	 * 节点属性ID
	 */
	public static final String ATTR_ID = "id";

	/**
	 * 节点名称
	 */
	public static final String ATTR_NAME = "name";

	/**
	 * 分类属性名称
	 */
	public static final String ATTR_CATEGORY = "category";

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
	public static final String ATTR_REMINDERTIME = "reminderTime";

	/**
	 * 
	 */
	public static final String ATTR_REMINDERREPEAT = "reminderRepeat";

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
	public static final String ATTR_SOURCEREF = "sourceRef";

	/**
	 * 
	 */
	public static final String ATTR_TARGETREF = "targetRef";

	/**
	 * 
	 */
	public static final String ELEMENT_CONDITIONEXPRESSION = "conditionExpression";

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

	/**
	 * 获取属性
	 * 
	 * @param element
	 * @param attrName
	 * @return
	 */
	public String getAttribute(Element element, String attrName)
	{
		String value = element.getAttribute(attrName);

		return StringUtils.trimToNull(value);
	}
}
