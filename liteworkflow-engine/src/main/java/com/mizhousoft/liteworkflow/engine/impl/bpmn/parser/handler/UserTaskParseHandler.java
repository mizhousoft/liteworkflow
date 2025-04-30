package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;

import com.mizhousoft.liteworkflow.bpmn.model.CustomProperty;
import com.mizhousoft.liteworkflow.bpmn.model.EventListenerElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowCC;
import com.mizhousoft.liteworkflow.bpmn.model.FlowCCType;
import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.FlowOperation;
import com.mizhousoft.liteworkflow.bpmn.model.MultiInstanceLoopCharacteristics;
import com.mizhousoft.liteworkflow.bpmn.model.SelectFromEnum;
import com.mizhousoft.liteworkflow.bpmn.model.SelectTargetType;
import com.mizhousoft.liteworkflow.bpmn.model.UserTaskModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.delegate.TaskListener;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.BpmnConstant;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.ElementParseUtils;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.NodeParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.util.DomUtils;

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
		task.setAssignee(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_ASSIGNEE));
		task.setFormKey(ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_FORM_KEY));

		SelectTargetType targetType = parseTargetType(element);
		task.setTargetType(targetType);

		SelectFromEnum selectFrom = parseSelectFrom(element);
		task.setSelectFrom(selectFrom);

		Set<String> optionalUserIds = new HashSet<>(10);
		String optionalUsers = ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_OPTIONAL_USERS);
		if (null != optionalUsers)
		{
			String[] values = optionalUsers.split(",");
			for (String value : values)
			{
				optionalUserIds.add(value);
			}
		}
		task.setOptionalUserIds(optionalUserIds);

		List<CustomProperty> custProperties = ElementParseUtils.parseCustomPropertyList(element);
		task.setCustomProperties(custProperties);

		List<EventListenerElement> eventListeners = parseEventListenerElements(element);
		task.setEventListeners(eventListeners);

		MultiInstanceLoopCharacteristics loopCharacteristics = ElementParseUtils.parseLoopCharacteristics(element);
		task.setLoopCharacteristics(loopCharacteristics);

		List<FlowOperation> operations = parseOperationElements(element);
		task.setOperations(operations);

		List<FlowCC> cclist = parseCClistElements(element);
		task.setCclist(cclist);
	}

	private SelectTargetType parseTargetType(Element element)
	{
		String targetType = ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_TARGET_TYPE);
		if (null != targetType)
		{
			SelectTargetType selectTargetType = SelectTargetType.get(targetType);
			if (null == selectTargetType)
			{
				throw new WorkFlowException("TargetType is illegal, value is " + StringUtils.left(targetType, 32));
			}

			return selectTargetType;
		}

		return null;
	}

	private SelectFromEnum parseSelectFrom(Element element)
	{
		String selectFrom = ElementParseUtils.getAttribute(element, BpmnConstant.ATTR_SELECT_FROM);
		if (null != selectFrom)
		{
			SelectFromEnum selectFromEnum = SelectFromEnum.get(selectFrom);
			if (null == selectFromEnum)
			{
				throw new WorkFlowException("SelectFromEnum is illegal, value is " + StringUtils.left(selectFrom, 32));
			}

			return selectFromEnum;
		}

		return null;
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

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, BpmnConstant.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, BpmnConstant.NODE_TASK_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = ElementParseUtils.getAttribute(listenerElement, BpmnConstant.ATTR_EVENT);
				String clazz = ElementParseUtils.getAttribute(listenerElement, BpmnConstant.ATTR_CLASS);
				String delegateExpression = ElementParseUtils.getAttribute(listenerElement, BpmnConstant.ATTR_DELEGATE_EXPRESSION);

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

	/**
	 * 解析操作元素
	 * 
	 * @param taskElement
	 * @return
	 */
	private List<FlowOperation> parseOperationElements(Element taskElement)
	{
		List<FlowOperation> operations = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, BpmnConstant.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			Element operationsElement = DomUtils.listFirstChildElement(extensionElement, BpmnConstant.ELEMENT_OPERATIONS);
			if (null != operationsElement)
			{
				List<Element> operationElements = DomUtils.listChildElements(operationsElement, BpmnConstant.ELEMENT_OPERATION);
				for (Element operationElement : operationElements)
				{
					String id = ElementParseUtils.getAttribute(operationElement, BpmnConstant.ATTR_ID);
					String enable = ElementParseUtils.getAttribute(operationElement, BpmnConstant.ATTR_ENABLE);
					String label = ElementParseUtils.getAttribute(operationElement, BpmnConstant.ATTR_LABEL);
					String toNodeIdsStr = ElementParseUtils.getAttribute(operationElement, BpmnConstant.ATTR_TONODEIDS);
					String remarkEnable = ElementParseUtils.getAttribute(operationElement, BpmnConstant.ATTR_REMARK_ENABLE);
					String remarkRequired = ElementParseUtils.getAttribute(operationElement, BpmnConstant.ATTR_REMARKR_EQUIRED);

					FlowOperation.Remark remark = new FlowOperation.Remark();
					remark.setEnable(BooleanUtils.toBoolean(remarkEnable));
					remark.setRequired(BooleanUtils.toBoolean(remarkRequired));

					FlowOperation operation = new FlowOperation();
					operation.setId(id);
					operation.setEnable(BooleanUtils.toBoolean(enable));
					operation.setLabel(label);
					operation.setRemark(remark);

					List<FlowElement> backToNodes = new ArrayList<>(5);
					if (!StringUtils.isBlank(toNodeIdsStr))
					{
						String[] toNodeIds = toNodeIdsStr.split(",");
						for (String toNodeId : toNodeIds)
						{
							FlowElement backToNode = new FlowElement();
							backToNode.setId(toNodeId);

							backToNodes.add(backToNode);
						}
					}
					operation.setBackToNodes(backToNodes);

					operations.add(operation);
				}
			}
		}

		return operations;
	}

	/**
	 * 解析抄送人元素
	 * 
	 * @param taskElement
	 * @return
	 */
	private List<FlowCC> parseCClistElements(Element taskElement)
	{
		List<FlowCC> cclist = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, BpmnConstant.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			Element cclistElement = DomUtils.listFirstChildElement(extensionElement, BpmnConstant.ELEMENT_CCLIST);
			if (null != cclistElement)
			{
				List<Element> ccElements = DomUtils.listChildElements(cclistElement, BpmnConstant.ELEMENT_CC);
				for (Element ccElement : ccElements)
				{
					String type = ElementParseUtils.getAttribute(ccElement, BpmnConstant.ATTR_TYPE);
					String value = ElementParseUtils.getAttribute(ccElement, BpmnConstant.ATTR_VALUE);

					FlowCC cc = new FlowCC();
					cc.setType(type);
					cc.setValue(value);

					Set<String> userIds = new HashSet<>(5);
					if (FlowCCType.TARGET_USER.isSelf(type) && null != value)
					{
						String[] ids = value.split(",");
						for (String id : ids)
						{
							userIds.add(id);
						}
					}
					cc.setCcUserIds(userIds);

					cclist.add(cc);
				}
			}
		}

		return cclist;
	}
}
