package com.mizhousoft.liteworkflow.engine.impl.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * DOM工具类
 *
 * @version
 */
public abstract class DomUtils
{
	/**
	 * 从element元素查找所有tagName指定的子节点元素集合
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static List<Element> listChildElements(Element element, String tagName)
	{
		if (element == null || !element.hasChildNodes())
		{
			return Collections.emptyList();
		}

		List<Element> childElements = new ArrayList<Element>(5);

		for (Node child = element.getFirstChild(); child != null; child = child.getNextSibling())
		{
			if (child.getNodeType() == Node.ELEMENT_NODE)
			{
				Element childElement = (Element) child;
				String childTagName = childElement.getNodeName();
				if (tagName.equals(childTagName))
				{
					childElements.add(childElement);
				}
			}
		}

		return childElements;
	}

	/**
	 * 查找出第一个子元素
	 * 
	 * @param element
	 * @param tagName
	 * @return
	 */
	public static Element listFirstChildElement(Element element, String tagName)
	{
		List<Element> childElements = listChildElements(element, tagName);
		if (!childElements.isEmpty())
		{
			return childElements.get(0);
		}

		return null;
	}
}
