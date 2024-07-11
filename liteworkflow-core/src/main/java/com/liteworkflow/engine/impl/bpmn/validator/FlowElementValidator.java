package com.liteworkflow.engine.impl.bpmn.validator;

import com.liteworkflow.engine.model.FlowElement;

/**
 * 验证器
 *
 * @version
 */
public interface FlowElementValidator
{
	/**
	 * 验证
	 * 
	 * @param flowElement
	 */
	void validate(FlowElement flowElement);
}
