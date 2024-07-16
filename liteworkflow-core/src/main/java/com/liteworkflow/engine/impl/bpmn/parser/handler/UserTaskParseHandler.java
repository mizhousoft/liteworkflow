package com.liteworkflow.engine.impl.bpmn.parser.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.delegate.TaskListener;
import com.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.liteworkflow.engine.model.EventListenerElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.UserTaskModel;
import com.liteworkflow.engine.util.DomUtils;

/**
 * 任务节点解析类
 * 
 * @version
 */
public class UserTaskParseHandler extends NodeParseHandler
{
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isSupported(String elementName)
	{
		return "userTask".equals(elementName);
	}

	/**
	 * {@inheritDoc}
	 */
	protected FlowNode newFlowNode()
	{
		return new UserTaskModel();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void doParse(FlowNode node, Element element)
	{
		UserTaskModel task = (UserTaskModel) node;
		task.setAssignee(getAttribute(element, ATTR_ASSIGNEE));
		task.setExpireTime(getAttribute(element, ATTR_EXPIRETIME));
		task.setReminderTime(getAttribute(element, ATTR_REMINDERTIME));
		task.setReminderRepeat(getAttribute(element, ATTR_REMINDERREPEAT));

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
				String event = getAttribute(listenerElement, ATTR_EVENT);
				String clazz = getAttribute(listenerElement, ATTR_CLASS);
				String delegateExpression = getAttribute(listenerElement, ATTR_DELEGATE_EXPRESSION);

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
				else
				{
					throw new WorkFlowException("EventListenerElement is wrong.");
				}

				if (!TaskListener.EVENTNAME_CREATE.equals(event) && !TaskListener.EVENTNAME_ASSIGNMENT.equals(event)
				        && !TaskListener.EVENTNAME_COMPLETE.equals(event) && !TaskListener.EVENTNAME_DELETE.equals(event)
				        && !TaskListener.EVENTNAME_ALL_EVENTS.equals(event))
				{
					throw new WorkFlowException("EventListenerElement event is wrong.");
				}

				eventListeners.add(eventListener);
			}
		}

		return eventListeners;
	}
}
