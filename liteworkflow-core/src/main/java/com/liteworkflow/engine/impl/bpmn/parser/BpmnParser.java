package com.liteworkflow.engine.impl.bpmn.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.delegate.ExecutionListener;
import com.liteworkflow.engine.impl.bpmn.parser.handler.EndEventParseHandler;
import com.liteworkflow.engine.impl.bpmn.parser.handler.ExclusiveGatewayParseHandler;
import com.liteworkflow.engine.impl.bpmn.parser.handler.ForkGatewayParseHandler;
import com.liteworkflow.engine.impl.bpmn.parser.handler.JoinGatewayParseHandler;
import com.liteworkflow.engine.impl.bpmn.parser.handler.SequenceFlowParseHandler;
import com.liteworkflow.engine.impl.bpmn.parser.handler.StartEventParseHandler;
import com.liteworkflow.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.EventListenerElement;
import com.liteworkflow.engine.model.FlowElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.SequenceFlowModel;
import com.liteworkflow.engine.util.DomUtils;

/**
 * 流程定义xml文件的模型解析器
 * 
 * @version
 */
public class BpmnParser
{
	/**
	 * DocumentBuilderFactory
	 */
	private static final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	/**
	 * 解析处理器列表
	 */
	private static final List<ElementParseHandler> PARSE_HANDLER_LIST = new ArrayList<>(10)
	{
		private static final long serialVersionUID = 2323475780811875175L;

		{
			add(new StartEventParseHandler());
			add(new UserTaskParseHandler());
			add(new ExclusiveGatewayParseHandler());
			add(new ForkGatewayParseHandler());
			add(new JoinGatewayParseHandler());
			add(new EndEventParseHandler());
			add(new SequenceFlowParseHandler());
		}
	};

	/**
	 * 解析流程定义文件，并将解析后的对象放入模型容器中
	 * 
	 * @param bytes
	 */
	public static BpmnModel parse(byte[] bytes)
	{
		try
		{
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document doc = documentBuilder.parse(new ByteArrayInputStream(bytes));
			Element processE = doc.getDocumentElement();

			BpmnModel bpmnModel = new BpmnModel();
			bpmnModel.setId(StringUtils.trimToNull(processE.getAttribute(ElementParseHandler.ATTR_ID)));
			bpmnModel.setName(StringUtils.trimToNull(processE.getAttribute(ElementParseHandler.ATTR_NAME)));
			bpmnModel.setCategory(StringUtils.trimToNull(processE.getAttribute(ElementParseHandler.ATTR_CATEGORY)));

			List<FlowElement> flowElements = parseNodeList(processE.getChildNodes());
			handleSequenceFlowRef(flowElements);

			bpmnModel.setFlowElements(flowElements);

			List<EventListenerElement> eventListeners = parseEventListenerElements(processE);
			bpmnModel.setEventListeners(eventListeners);

			return bpmnModel;
		}
		catch (SAXException | IOException | ParserConfigurationException e)
		{
			throw new WorkFlowException("Model xml parse failed.", e);
		}
	}

	/**
	 * 解析节点元素列表
	 * 
	 * @param nodeElementList
	 * @return
	 */
	private static List<FlowElement> parseNodeList(NodeList nodeElementList)
	{
		List<FlowElement> flowElements = new ArrayList<>(10);

		for (int i = 0; i < nodeElementList.getLength(); i++)
		{
			Node node = nodeElementList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				FlowElement flowElement = parseNodeElement((Element) node);
				if (null != flowElement)
				{
					flowElements.add(flowElement);
				}
			}
		}

		return flowElements;
	}

	/**
	 * 对流程定义xml的节点，根据其节点对应的解析器解析节点内容
	 * 
	 * @param node
	 * @return
	 */
	private static FlowElement parseNodeElement(Element element)
	{
		String nodeName = element.getNodeName();

		ElementParseHandler parseHandler = getElementParseHandler(nodeName);
		if (null == parseHandler)
		{
			return null;
		}

		FlowElement flowElement = parseHandler.parse(element);

		return flowElement;
	}

	/**
	 * 根据节点名称获取元素解析处理器
	 * 
	 * @param nodeName
	 * @return
	 */
	private static ElementParseHandler getElementParseHandler(String nodeName)
	{
		for (ElementParseHandler parseHandler : PARSE_HANDLER_LIST)
		{
			if (parseHandler.isSupported(nodeName))
			{
				return parseHandler;
			}
		}

		return null;
	}

	/**
	 * 处理顺序流关联
	 * 
	 * @param flowElements
	 */
	private static void handleSequenceFlowRef(List<FlowElement> flowElements)
	{
		List<FlowNode> flowNodes = new ArrayList<>(flowElements.size());

		for (FlowElement flowElement : flowElements)
		{
			if (flowElement instanceof FlowNode flowNode)
			{
				flowNodes.add(flowNode);
			}
		}

		for (FlowNode flowNode : flowNodes)
		{
			List<SequenceFlowModel> incomingFlows = new ArrayList<>(5);
			List<SequenceFlowModel> outgoingFlows = new ArrayList<>(5);

			for (FlowElement flowElement : flowElements)
			{
				if (flowElement instanceof SequenceFlowModel sequenceFlow)
				{
					if (flowNode.getId().equals(sequenceFlow.getSourceRef()))
					{
						sequenceFlow.setSourceNode(flowNode);
						outgoingFlows.add(sequenceFlow);
					}
					else if (flowNode.getId().equals(sequenceFlow.getTargetRef()))
					{
						incomingFlows.add(sequenceFlow);
						sequenceFlow.setTargetNode(flowNode);
					}
				}
			}

			flowNode.setIncomingFlows(incomingFlows);
			flowNode.setOutgoingFlows(outgoingFlows);
		}
	}

	/**
	 * 解析事件监听器元素
	 * 
	 * @param taskElement
	 * @return
	 */
	private static List<EventListenerElement> parseEventListenerElements(Element taskElement)
	{
		List<EventListenerElement> eventListeners = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, ElementParseHandler.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, ElementParseHandler.NODE_EXECUTION_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = StringUtils.trimToNull(listenerElement.getAttribute(ElementParseHandler.ATTR_EVENT));
				String clazz = StringUtils.trimToNull(listenerElement.getAttribute(ElementParseHandler.ATTR_CLASS));
				String delegateExpression = StringUtils
				        .trimToNull(listenerElement.getAttribute(ElementParseHandler.ATTR_DELEGATE_EXPRESSION));

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

				if (!ExecutionListener.EVENTNAME_START.equals(event) && !ExecutionListener.EVENTNAME_END.equals(event)
				        && !ExecutionListener.EVENTNAME_TAKE.equals(event))
				{
					throw new WorkFlowException("EventListenerElement event is wrong.");
				}

				eventListeners.add(eventListener);
			}
		}

		return eventListeners;
	}
}
