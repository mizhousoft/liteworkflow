package com.liteworkflow.engine.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * xml解析的帮助类
 * 
 * @author yuqs
 * @since 1.0
 */
public class XmlUtils
{
	/**
	 * DocumentBuilderFactory实例
	 */
	private static DocumentBuilderFactory documentBuilderFactory = createDocumentBuilderFactory();

	/**
	 * 获取DocumentBuilderFactory
	 * 
	 * @return
	 */
	private static DocumentBuilderFactory createDocumentBuilderFactory()
	{
		return DocumentBuilderFactory.newInstance();
	}

	/**
	 * 由DocumentBuilderFactory产生DocumentBuilder实例
	 * 
	 * @return
	 */
	public static DocumentBuilder createDocumentBuilder()
	{
		try
		{
			return documentBuilderFactory.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			return null;
		}
	}

	/**
	 * 从element元素查找所有tagName指定的子节点元素集合
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static List<Element> elements(Element element, String tagName)
	{
		if (element == null || !element.hasChildNodes())
		{
			return Collections.emptyList();
		}

		List<Element> elements = new ArrayList<Element>();
		for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling())
		{
			if (child.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) child;
				String childTagName = childElement.getNodeName();
				if (tagName.equals(childTagName))
					elements.add(childElement);
			}
		}
		return elements;
	}
}
