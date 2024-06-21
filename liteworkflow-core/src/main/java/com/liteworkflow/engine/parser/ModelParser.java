package com.liteworkflow.engine.parser;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.liteworkflow.ProcessException;
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
			process.setExpireTime(processE.getAttribute(NodeParser.ATTR_EXPIRETIME));
			process.setInstanceUrl(processE.getAttribute(NodeParser.ATTR_INSTANCEURL));

			NodeList nodeList = processE.getChildNodes();
			int nodeSize = nodeList.getLength();
			for (int i = 0; i < nodeSize; i++)
			{
				Node node = nodeList.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE)
				{
					NodeModel model = parseModel(node);
					process.getNodes().add(model);
				}
			}

			// 循环节点模型，构造变迁输入、输出的source、target
			for (NodeModel node : process.getNodes())
			{
				for (TransitionModel transition : node.getOutputs())
				{
					String to = transition.getTo();
					for (NodeModel node2 : process.getNodes())
					{
						if (to.equalsIgnoreCase(node2.getName()))
						{
							node2.getInputs().add(transition);
							transition.setTarget(node2);
						}
					}
				}
			}

			return process;
		}
		catch (SAXException | IOException | ParserConfigurationException e)
		{
			throw new ProcessException("Model xml parse failed.", e);
		}
	}

	/**
	 * 对流程定义xml的节点，根据其节点对应的解析器解析节点内容
	 * 
	 * @param node
	 * @return
	 */
	private static NodeModel parseModel(Node node)
	{
		String nodeName = node.getNodeName();
		NodeParser nodeParser = getNodeParser(nodeName);

		Element element = (Element) node;
		nodeParser.parse(element);

		return nodeParser.getModel();
	}

	public static NodeParser getNodeParser(String nodeName)
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
}
