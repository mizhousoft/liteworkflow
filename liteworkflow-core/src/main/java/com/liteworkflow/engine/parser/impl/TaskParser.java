package com.liteworkflow.engine.parser.impl;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.AssignmentHandler;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;
import com.mizhousoft.commons.lang.ClassUtils;

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
	protected NodeModel newModel()
	{
		return new TaskModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "task";
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParseNode(NodeModel node, Element element)
	{
		TaskModel task = (TaskModel) node;
		task.setForm(element.getAttribute(ATTR_FORM));
		task.setAssignee(element.getAttribute(ATTR_ASSIGNEE));
		task.setExpireTime(element.getAttribute(ATTR_EXPIRETIME));
		task.setAutoExecute(element.getAttribute(ATTR_AUTOEXECUTE));
		task.setReminderTime(element.getAttribute(ATTR_REMINDERTIME));
		task.setReminderRepeat(element.getAttribute(ATTR_REMINDERREPEAT));
		task.setPerformType(element.getAttribute(ATTR_PERFORMTYPE));
		task.setTaskType(element.getAttribute(ATTR_TASKTYPE));
		task.setAssignmentHandler(element.getAttribute(ATTR_ASSIGNEE_HANDLER));

		if (!StringUtils.isBlank(task.getAssignmentHandler()))
		{
			try
			{
				AssignmentHandler assignmentHandler = (AssignmentHandler) ClassUtils.newInstance(task.getAssignmentHandler(),
				        this.getClass().getClassLoader());
				task.setAssignmentHandlerObject(assignmentHandler);
			}
			catch (Exception e)
			{
				throw new IllegalArgumentException(task.getAssignmentHandler() + " is not implment AssignmentHandler.", e);
			}
		}
	}
}
