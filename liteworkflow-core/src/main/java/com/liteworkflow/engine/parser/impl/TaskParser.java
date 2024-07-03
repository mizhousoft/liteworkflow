package com.liteworkflow.engine.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.AssignmentHandler;
import com.liteworkflow.engine.model.ListenerModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;
import com.liteworkflow.engine.util.DomUtils;
import com.mizhousoft.commons.lang.ClassUtils;

/**
 * 任务节点解析类
 * 
 * @version
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

		List<ListenerModel> listenerModels = parseExtensionElements(element);
		task.setListenerModels(listenerModels);
	}

	/**
	 * 解析扩展元素
	 * 
	 * @param taskElement
	 * @return
	 */
	private List<ListenerModel> parseExtensionElements(Element taskElement)
	{
		List<ListenerModel> listenerModels = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, NODE_TASK_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = listenerElement.getAttribute(ATTR_EVENT);
				String clazz = listenerElement.getAttribute(ATTR_CLASS);
				String delegateExpression = listenerElement.getAttribute(ATTR_DELEGATE_EXPRESSION);

				ListenerModel listenerModel = new ListenerModel();
				listenerModel.setEvent(event);

				if (!StringUtils.isBlank(clazz))
				{
					listenerModel.setImplementationType(ListenerModel.IMPLEMENTATION_TYPE_CLASS);
					listenerModel.setImplementation(clazz);
				}
				else if (!StringUtils.isBlank(delegateExpression))
				{
					listenerModel.setImplementationType(ListenerModel.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
					listenerModel.setImplementation(delegateExpression);
				}

				listenerModels.add(listenerModel);
			}
		}

		return listenerModels;
	}
}
