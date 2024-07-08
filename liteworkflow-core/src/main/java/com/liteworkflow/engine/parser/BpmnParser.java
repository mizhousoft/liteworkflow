package com.liteworkflow.engine.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.liteworkflow.WorkFlowException;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.EventListenerElement;
import com.liteworkflow.engine.model.FlowNode;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.parser.impl.EndEventParser;
import com.liteworkflow.engine.parser.impl.ExclusiveGatewayParser;
import com.liteworkflow.engine.parser.impl.ForkGatewayParser;
import com.liteworkflow.engine.parser.impl.JoinGatewayParser;
import com.liteworkflow.engine.parser.impl.SequenceFlowParser;
import com.liteworkflow.engine.parser.impl.StartEventParser;
import com.liteworkflow.engine.parser.impl.UserTaskParser;
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
	 * 节点解析器列表
	 */
	private static final List<NodeParser> NODE_PARSER_LIST = new ArrayList<>(10)
	{
		private static final long serialVersionUID = 2323475780811875175L;

		{
			add(new StartEventParser());
			add(new UserTaskParser());
			add(new ExclusiveGatewayParser());
			add(new ForkGatewayParser());
			add(new JoinGatewayParser());
			add(new EndEventParser());
			add(new SequenceFlowParser());
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
			bpmnModel.setId(processE.getAttribute(NodeParser.ATTR_ID));
			bpmnModel.setDisplayName(processE.getAttribute(NodeParser.ATTR_DISPLAYNAME));
			bpmnModel.setCategory(StringUtils.trimToNull(processE.getAttribute(NodeParser.ATTR_CATEGORY)));
			bpmnModel.setExpireTime(processE.getAttribute(NodeParser.ATTR_EXPIRETIME));

			List<FlowNode> nodeModels = parseNodeList(processE.getChildNodes());
			bpmnModel.setNodeModels(nodeModels);

			List<EventListenerElement> eventListeners = parseEventListenerElements(processE);
			bpmnModel.setEventListeners(eventListeners);

			checkProcessModel(bpmnModel);

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
	private static List<FlowNode> parseNodeList(NodeList nodeElementList)
	{
		List<FlowNode> nodeModels = new ArrayList<FlowNode>(10);

		for (int i = 0; i < nodeElementList.getLength(); i++)
		{
			Node node = nodeElementList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				FlowNode nodeModel = parseNodeElement((Element) node);
				if (null != nodeModel)
				{
					nodeModels.add(nodeModel);
				}
			}
		}

		// 循环节点模型，构造变迁输入、输出的source、target
		for (FlowNode node : nodeModels)
		{
			for (TransitionModel transition : node.getOutputs())
			{
				String to = transition.getTo();

				for (FlowNode node2 : nodeModels)
				{
					if (to.equalsIgnoreCase(node2.getId()))
					{
						List<TransitionModel> inputs = new ArrayList<>(node2.getInputs());
						inputs.add(transition);
						node2.setInputs(inputs);

						transition.setTarget(node2);
					}
				}
			}
		}

		return nodeModels;
	}

	/**
	 * 对流程定义xml的节点，根据其节点对应的解析器解析节点内容
	 * 
	 * @param node
	 * @return
	 */
	private static FlowNode parseNodeElement(Element element)
	{
		String nodeName = element.getNodeName();
		if (NodeParser.NODE_EXTENSION_ELEMENTS.equals(nodeName))
		{
			return null;
		}

		NodeParser nodeParser = getNodeParser(nodeName);

		nodeParser.parse(element);

		return nodeParser.getModel();
	}

	/**
	 * 根据节点名称获取节点解析器
	 * 
	 * @param nodeName
	 * @return
	 */
	private static NodeParser getNodeParser(String nodeName)
	{
		for (NodeParser nodeParser : NODE_PARSER_LIST)
		{
			if (nodeParser.getNodeName().equals(nodeName))
			{
				return nodeParser;
			}
		}

		throw new WorkFlowException(nodeName + " NodeParser not found.");
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

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, NodeParser.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, NodeParser.NODE_EXECUTION_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = listenerElement.getAttribute(NodeParser.ATTR_EVENT);
				String clazz = listenerElement.getAttribute(NodeParser.ATTR_CLASS);
				String delegateExpression = listenerElement.getAttribute(NodeParser.ATTR_DELEGATE_EXPRESSION);

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

	/**
	 * 校验模型是否正确
	 * 
	 * @param bpmnModel
	 */
	private static void checkProcessModel(BpmnModel bpmnModel)
	{
		Assert.notNull(bpmnModel.getId(), "Process id is null.");
		Assert.notNull(bpmnModel.getDisplayName(), "Process display name is null.");
		Assert.notNull(bpmnModel.getStartModel(), "Process start node not exist.");
		Assert.notNull(bpmnModel.getEndModel(), "Process end node not exist.");

		List<FlowNode> nodeModels = bpmnModel.getNodeModels();
		for (FlowNode nodeModel : nodeModels)
		{
			List<TransitionModel> outputs = nodeModel.getOutputs();
			for (TransitionModel output : outputs)
			{
				if (!nodeModels.stream().anyMatch(nm -> nm.getId().equals(output.getTo())))
				{
					throw new WorkFlowException("Transition to is wrong, can not associate node.");
				}
			}
		}
	}
}
