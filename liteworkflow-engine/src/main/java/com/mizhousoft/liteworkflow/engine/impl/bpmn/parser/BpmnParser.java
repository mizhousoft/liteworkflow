package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.bpmn.model.CustomProperty;
import com.mizhousoft.liteworkflow.bpmn.model.EventListenerElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowElement;
import com.mizhousoft.liteworkflow.bpmn.model.FlowNode;
import com.mizhousoft.liteworkflow.bpmn.model.SequenceFlowModel;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.delegate.ExecutionListener;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.EndEventParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.ExclusiveGatewayParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.JoinGatewayParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.ParallelGatewayParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.SequenceFlowParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.StartEventParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.bpmn.parser.handler.UserTaskParseHandler;
import com.mizhousoft.liteworkflow.engine.impl.util.DomUtils;

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
			add(new ParallelGatewayParseHandler());
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
			documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			documentBuilderFactory.setNamespaceAware(true);

			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document doc = documentBuilder.parse(new ByteArrayInputStream(bytes));
			Element processE = doc.getDocumentElement();

			BpmnModel bpmnModel = new BpmnModel();
			bpmnModel.setId(StringUtils.trimToNull(processE.getAttribute(BpmnConstant.ATTR_ID)));
			bpmnModel.setName(StringUtils.trimToNull(processE.getAttribute(BpmnConstant.ATTR_NAME)));
			bpmnModel.setCategory(StringUtils.trimToNull(processE.getAttribute(BpmnConstant.ATTR_CATEGORY)));

			List<FlowElement> flowElements = parseNodeList(processE.getChildNodes());
			handleSequenceFlowRef(flowElements);

			bpmnModel.setFlowElements(flowElements);

			List<EventListenerElement> eventListeners = parseEventListenerElements(processE);
			bpmnModel.setEventListeners(eventListeners);

			String documentation = parseDocumentation(processE);
			bpmnModel.setDocumentation(documentation);

			List<CustomProperty> custProperties = ElementParseUtils.parseCustomPropertyList(processE);
			bpmnModel.setCustomProperties(custProperties);

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
	 * @param processElement
	 * @return
	 */
	private static List<EventListenerElement> parseEventListenerElements(Element processElement)
	{
		List<EventListenerElement> eventListeners = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(processElement, BpmnConstant.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, BpmnConstant.NODE_EXECUTION_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = StringUtils.trimToNull(listenerElement.getAttribute(BpmnConstant.ATTR_EVENT));
				String clazz = StringUtils.trimToNull(listenerElement.getAttribute(BpmnConstant.ATTR_CLASS));
				String delegateExpression = StringUtils.trimToNull(listenerElement.getAttribute(BpmnConstant.ATTR_DELEGATE_EXPRESSION));

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
				        && !ExecutionListener.EVENTNAME_CANCELLED.equals(event))
				{
					throw new WorkFlowException("EventListenerElement event is wrong.");
				}

				eventListeners.add(eventListener);
			}
		}

		return eventListeners;
	}

	/**
	 * 解析文档描述
	 * 
	 * @param processElement
	 * @return
	 */
	private static String parseDocumentation(Element processElement)
	{
		Element element = DomUtils.listFirstChildElement(processElement, BpmnConstant.NODE_DOCUMENTATION);
		if (null != element)
		{
			String documentation = element.getTextContent();
			documentation = StringUtils.trimToNull(documentation);

			return documentation;
		}

		return null;
	}
}
