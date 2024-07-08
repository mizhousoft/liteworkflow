package com.liteworkflow.engine.parser.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.engine.model.EventListenerElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.parser.AbstractNodeParser;
import com.liteworkflow.engine.util.DomUtils;

/**
 * 任务节点解析类
 * 
 * @version
 */
public class UserTaskParser extends AbstractNodeParser
{
	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newModel()
	{
		return new UserTaskModel();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNodeName()
	{
		return "userTask";
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParseNode(FlowNode node, Element element)
	{
		UserTaskModel task = (UserTaskModel) node;
		task.setAssignee(element.getAttribute(ATTR_ASSIGNEE));
		task.setExpireTime(element.getAttribute(ATTR_EXPIRETIME));
		task.setAutoExecute(element.getAttribute(ATTR_AUTOEXECUTE));
		task.setReminderTime(element.getAttribute(ATTR_REMINDERTIME));
		task.setReminderRepeat(element.getAttribute(ATTR_REMINDERREPEAT));
		task.setPerformType(element.getAttribute(ATTR_PERFORMTYPE));

		List<EventListenerElement> eventListeners = parseEventListenerElements(element);
		task.setEventListeners(eventListeners);
	}

	/**
	 * 解析扩展元素
	 * 
	 * @param taskElement
	 * @return
	 */
	private List<EventListenerElement> parseEventListenerElements(Element taskElement)
	{
		List<EventListenerElement> eventListeners = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, NODE_TASK_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = listenerElement.getAttribute(ATTR_EVENT);
				String clazz = listenerElement.getAttribute(ATTR_CLASS);
				String delegateExpression = listenerElement.getAttribute(ATTR_DELEGATE_EXPRESSION);

				EventListenerElement eventListener = new EventListenerElement();
				eventListener.setEvent(event);

				if (!StringUtils.isBlank(clazz))
				{
					eventListener.setImplementationType(EventListenerElement.IMPLEMENTATION_TYPE_CLASS);
					eventListener.setImplementation(clazz);
				}
				else if (!StringUtils.isBlank(delegateExpression))
				{
					eventListener.setImplementationType(EventListenerElement.IMPLEMENTATION_TYPE_DELEGATEEXPRESSION);
					eventListener.setImplementation(delegateExpression);
				}

				eventListeners.add(eventListener);
			}
		}

		return eventListeners;
	}
}
