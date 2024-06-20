package com.liteworkflow.engine.parser.impl;

import org.w3c.dom.Element;

import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;

/**
 * 任务节点解析类
 * 
 * @author
 * @since 1.0
 */
public class TaskParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "task";
	}

	/**
	 * 由于任务节点需要解析form、assignee属性，这里覆盖抽象类方法实现
	 */
	protected void doParseNode(NodeModel node, Element element)
	{
		TaskModel task = (TaskModel) node;
		task.setForm(element.getAttribute(ATTR_FORM));
		task.setAssignee(element.getAttribute(ATTR_ASSIGNEE));
		task.setExpireTime(element.getAttribute(ATTR_EXPIRETIME));
		task.setAutoExecute(element.getAttribute(ATTR_AUTOEXECUTE));
		task.setCallback(element.getAttribute(ATTR_CALLBACK));
		task.setReminderTime(element.getAttribute(ATTR_REMINDERTIME));
		task.setReminderRepeat(element.getAttribute(ATTR_REMINDERREPEAT));
		task.setPerformType(element.getAttribute(ATTR_PERFORMTYPE));
		task.setTaskType(element.getAttribute(ATTR_TASKTYPE));
		task.setAssignmentHandler(element.getAttribute(ATTR_ASSIGNEE_HANDLER));
	}

	/**
	 * 产生TaskModel模型对象
	 */
	protected NodeModel newModel()
	{
		return new TaskModel();
	}
}
