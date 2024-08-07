package com.liteworkflow.engine.impl.command;

import java.util.List;

import org.springframework.util.Assert;

import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.bpmn.parser.BpmnParser;
import com.liteworkflow.engine.impl.bpmn.validator.FlowElementValidator;
import com.liteworkflow.engine.impl.bpmn.validator.FlowElementValidatorFactory;
import com.liteworkflow.engine.model.BpmnModel;
import com.liteworkflow.engine.model.FlowElement;

/**
 * BPMN模型解析命令
 *
 * @version
 */
public class BpmnParseCommand implements Command<BpmnModel>
{
	/**
	 * 数据字节
	 */
	private byte[] bytes;

	/**
	 * 构造函数
	 *
	 * @param bytes
	 */
	public BpmnParseCommand(byte[] bytes)
	{
		super();
		this.bytes = bytes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BpmnModel execute(CommandContext context)
	{
		BpmnModel bpmnModel = BpmnParser.parse(bytes);

		validateBpmnModel(bpmnModel);

		return bpmnModel;
	}

	/**
	 * 校验模型是否正确
	 * 
	 * @param bpmnModel
	 */
	private void validateBpmnModel(BpmnModel bpmnModel)
	{
		Assert.notNull(bpmnModel.getId(), "Process id is null.");
		Assert.notNull(bpmnModel.getName(), "Process name is null.");
		Assert.notNull(bpmnModel.getStartModel(), "Process start node does not exist.");
		Assert.notNull(bpmnModel.getEndModel(), "Process end node does not exist.");

		List<FlowElement> flowElements = bpmnModel.getFlowElements();
		for (FlowElement flowElement : flowElements)
		{
			FlowElementValidator validator = FlowElementValidatorFactory.build(flowElement);
			validator.validate(flowElement);
		}
	}
}
