package com.mizhousoft.liteworkflow.engine.impl.bpmn.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mizhousoft.liteworkflow.bpmn.model.CustomProperty;
import com.mizhousoft.liteworkflow.bpmn.model.MultiInstanceLoopCharacteristics;
import com.mizhousoft.liteworkflow.engine.WorkFlowException;
import com.mizhousoft.liteworkflow.engine.impl.util.DomUtils;

/**
 * 解析工具类
 *
 * @version
 */
public abstract class ElementParseUtils
{
	/**
	 * 获取属性
	 * 
	 * @param element
	 * @param attrName
	 * @return
	 */
	public static String getAttribute(Element element, String attrName)
	{
		String value = element.getAttribute(attrName);

		return StringUtils.trimToNull(value);
	}

	/**
	 * 解析自定义属性
	 * 
	 * @param taskElement
	 * @return
	 */
	public static List<CustomProperty> parseCustomPropertyList(Element element)
	{
		List<CustomProperty> customProperties = new ArrayList<>(5);

		Element extensionElement = DomUtils.listFirstChildElement(element, BpmnConstant.NODE_EXTENSION_ELEMENTS);
		if (null != extensionElement)
		{
			Element propertiesElement = DomUtils.listFirstChildElement(extensionElement, BpmnConstant.ELEMENT_PROPERTIES);
			if (null != propertiesElement)
			{
				List<Element> propertyElements = DomUtils.listChildElements(propertiesElement, BpmnConstant.ELEMENT_PROPERTY);
				Set<String> names = new HashSet<>(propertyElements.size());

				for (Element propertyElement : propertyElements)
				{
					String id = getAttribute(propertyElement, BpmnConstant.ATTR_ID);
					String name = getAttribute(propertyElement, BpmnConstant.ATTR_NAME);
					String value = getAttribute(propertyElement, BpmnConstant.ATTR_VALUE);

					CustomProperty customProperty = new CustomProperty();
					customProperty.setId(id);
					customProperty.setName(name);
					customProperty.setValue(value);

					if (StringUtils.isBlank(name))
					{
						throw new WorkFlowException("Property name is blank.");
					}
					else if (names.contains(name))
					{
						throw new WorkFlowException("Property name exists, name is " + name);
					}

					names.add(name);
					customProperties.add(customProperty);
				}
			}
		}

		return customProperties;
	}

	/**
	 * 解析多实例特征元素
	 * 
	 * @param element
	 * @return
	 */
	public static MultiInstanceLoopCharacteristics parseLoopCharacteristics(Element element)
	{
		Element loopElement = DomUtils.listFirstChildElement(element, BpmnConstant.ELEMENT_LOOP_CHARACTERISTICS);
		if (null == loopElement)
		{
			return null;
		}

		Element conditionElement = DomUtils.listFirstChildElement(loopElement, BpmnConstant.ELEMENT_COMPLETION_CONDITION);
		String condition = parseTextContent(conditionElement);

		String isSequential = getAttribute(loopElement, BpmnConstant.ATTR_ISSEQUENTIAL);
		String collection = getAttribute(loopElement, BpmnConstant.ATTR_COLLECTION);
		String elementVariable = getAttribute(loopElement, BpmnConstant.ATTR_ELEMENTVARIABLE);

		MultiInstanceLoopCharacteristics loopCharacteristics = new MultiInstanceLoopCharacteristics();
		loopCharacteristics.setCollection(collection);
		loopCharacteristics.setElementVariable(elementVariable);
		loopCharacteristics.setCompletionCondition(condition);
		loopCharacteristics.setSequential(BooleanUtils.toBoolean(isSequential));

		return loopCharacteristics;
	}

	/**
	 * 解析内容
	 * 
	 * @param element
	 * @return
	 */
	public static String parseTextContent(Element element)
	{
		String textContent = null;

		NodeList childNodes = element.getChildNodes();
		for (int i = 0; i < childNodes.getLength(); ++i)
		{
			Node node = childNodes.item(i);
			String content = node.getTextContent();
			content = StringUtils.trimToNull(content);

			if (null != content)
			{
				textContent = content;
				break;
			}
		}

		return textContent;
	}
}
