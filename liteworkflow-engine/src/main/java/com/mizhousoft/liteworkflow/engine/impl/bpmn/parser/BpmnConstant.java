package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser;

/**
 * 常量
 *
 * @version
 */
public interface BpmnConstant
{
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
	 * 文档描述
	 */
	String NODE_DOCUMENTATION = "documentation";

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
	 * 发起人
	 */
	String ATTR_INITIATOR = "initiator";

	/**
	 * 
	 */
	String ATTR_FORM_KEY = "formKey";

	/**
	 * 
	 */
	String ATTR_ASSIGNEE = "assignee";

	/**
	 * 
	 */
	String ATTR_TARGET_TYPE = "targetType";

	/**
	 * 
	 */
	String ATTR_SELECT_FROM = "selectFrom";

	/**
	 * 
	 */
	String ATTR_OPTIONAL_USERS = "optionalUsers";

	/**
	 * 
	 */
	String ATTR_VERSION = "version";

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
	String ATTR_ENABLE = "enable";

	/**
	 * 
	 */
	String ATTR_LABEL = "label";

	/**
	 * 
	 */
	String ATTR_TONODEIDS = "toNodeIds";

	/**
	 * 
	 */
	String ATTR_REMARK_ENABLE = "remarkEnable";

	/**
	 * 
	 */
	String ATTR_REMARKR_EQUIRED = "remarkRequired";

	/**
	 * 
	 */
	String ATTR_TYPE = "type";

	/**
	 * 
	 */
	String ATTR_DELEGATE_EXPRESSION = "delegateExpression";

	/**
	 * 
	 */
	String ATTR_SOURCEREF = "sourceRef";

	/**
	 * 
	 */
	String ATTR_TARGETREF = "targetRef";

	/**
	 * 
	 */
	String ELEMENT_CONDITIONEXPRESSION = "conditionExpression";

	/**
	 * 
	 */
	String ELEMENT_PROPERTIES = "properties";

	/**
	 * 
	 */
	String ELEMENT_PROPERTY = "property";

	/**
	 * 值
	 */
	String ATTR_VALUE = "value";

	/**
	 * 
	 */
	String ELEMENT_LOOP_CHARACTERISTICS = "multiInstanceLoopCharacteristics";

	/**
	 * 
	 */
	String ELEMENT_COMPLETION_CONDITION = "completionCondition";

	/**
	 * 
	 */
	String ATTR_ISSEQUENTIAL = "isSequential";

	/**
	 * 
	 */
	String ATTR_COLLECTION = "collection";

	/**
	 * 
	 */
	String ATTR_ELEMENTVARIABLE = "elementVariable";

	/**
	 * 操作元素
	 */
	String ELEMENT_OPERATIONS = "operations";

	/**
	 * 操作元素
	 */
	String ELEMENT_OPERATION = "operation";

	/**
	 * 操作元素
	 */
	String ELEMENT_CCLIST = "cclist";

	/**
	 * 操作元素
	 */
	String ELEMENT_CC = "cc";
}
