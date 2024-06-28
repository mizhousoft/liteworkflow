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

import com.liteworkflow.ProcessException;
import com.liteworkflow.engine.model.ListenerModel;
import com.liteworkflow.engine.model.NodeModel;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.TransitionModel;
import com.liteworkflow.engine.parser.impl.DecisionParser;
import com.liteworkflow.engine.parser.impl.EndParser;
import com.liteworkflow.engine.parser.impl.ForkParser;
import com.liteworkflow.engine.parser.impl.JoinParser;
import com.liteworkflow.engine.parser.impl.StartParser;
import com.liteworkflow.engine.parser.impl.SubProcessParser;
import com.liteworkflow.engine.parser.impl.TaskParser;
import com.liteworkflow.engine.util.DomUtils;

/**
 * 流程定义xml文件的模型解析器
 * 
 * @author
 * @since 1.0
 */
public class ModelParser
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
			add(new StartParser());
			add(new TaskParser());
			add(new DecisionParser());
			add(new SubProcessParser());
			add(new ForkParser());
			add(new JoinParser());
			add(new EndParser());
		}
	};

	/**
	 * 解析流程定义文件，并将解析后的对象放入模型容器中
	 * 
	 * @param bytes
	 */
	public static ProcessModel parse(byte[] bytes)
	{
		try
		{
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

			Document doc = documentBuilder.parse(new ByteArrayInputStream(bytes));
			Element processE = doc.getDocumentElement();

			ProcessModel process = new ProcessModel();
			process.setName(processE.getAttribute(NodeParser.ATTR_NAME));
			process.setDisplayName(processE.getAttribute(NodeParser.ATTR_DISPLAYNAME));
			process.setCategory(StringUtils.trimToNull(processE.getAttribute(NodeParser.ATTR_CATEGORY)));
			process.setExpireTime(processE.getAttribute(NodeParser.ATTR_EXPIRETIME));
			process.setInstanceUrl(processE.getAttribute(NodeParser.ATTR_INSTANCEURL));

			List<NodeModel> nodeModels = parseNodeList(processE.getChildNodes());
			process.setNodeModels(nodeModels);

			List<ListenerModel> listenerModels = parseExtensionElements(processE);
			process.setListenerModels(listenerModels);

			checkProcessModel(process);

			return process;
		}
		catch (SAXException | IOException | ParserConfigurationException e)
		{
			throw new ProcessException("Model xml parse failed.", e);
		}
	}

	/**
	 * 解析节点元素列表
	 * 
	 * @param nodeElementList
	 * @return
	 */
	private static List<NodeModel> parseNodeList(NodeList nodeElementList)
	{
		List<NodeModel> nodeModels = new ArrayList<NodeModel>(10);

		for (int i = 0; i < nodeElementList.getLength(); i++)
		{
			Node node = nodeElementList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE)
			{
				NodeModel nodeModel = parseNodeElement((Element) node);
				if (null != nodeModel)
				{
					nodeModels.add(nodeModel);
				}
			}
		}

		// 循环节点模型，构造变迁输入、输出的source、target
		for (NodeModel node : nodeModels)
		{
			for (TransitionModel transition : node.getOutputs())
			{
				String to = transition.getTo();

				for (NodeModel node2 : nodeModels)
				{
					if (to.equalsIgnoreCase(node2.getName()))
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
	private static NodeModel parseNodeElement(Element element)
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

		throw new ProcessException(nodeName + " NodeParser not found.");
	}

	/**
	 * 解析扩展元素
	 * 
	 * @param taskElement
	 * @return
	 */
	private static List<ListenerModel> parseExtensionElements(Element taskElement)
	{
		List<ListenerModel> listenerModels = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(taskElement, NodeParser.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			List<Element> listenerElements = DomUtils.listChildElements(extensionElement, NodeParser.NODE_EXECUTION_LISTENER);
			for (Element listenerElement : listenerElements)
			{
				String event = listenerElement.getAttribute(NodeParser.ATTR_EVENT);
				String clazz = listenerElement.getAttribute(NodeParser.ATTR_CLASS);
				String delegateExpression = listenerElement.getAttribute(NodeParser.ATTR_DELEGATE_EXPRESSION);

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

	/**
	 * 校验模型是否正确
	 * 
	 * @param process
	 */
	private static void checkProcessModel(ProcessModel process)
	{
		Assert.notNull(process.getName(), "Process name is null.");
		Assert.notNull(process.getDisplayName(), "Process display name is null.");
		Assert.notNull(process.getStartModel(), "Process start node not exist.");
		Assert.notNull(process.getEndModel(), "Process end node not exist.");

		List<NodeModel> nodeModels = process.getNodeModels();
		for (NodeModel nodeModel : nodeModels)
		{
			List<TransitionModel> outputs = nodeModel.getOutputs();
			for (TransitionModel output : outputs)
			{
				if (!nodeModels.stream().anyMatch(nm -> nm.getName().equals(output.getTo())))
				{
					throw new ProcessException("Transition to is wrong, can not associate node.");
				}
			}
		}
	}
}
