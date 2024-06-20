package com.liteworkflow.engine.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * xml解析的帮助类
 * 
 * @author
 * @since 1.0
 */
public class DomUtils
{
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
