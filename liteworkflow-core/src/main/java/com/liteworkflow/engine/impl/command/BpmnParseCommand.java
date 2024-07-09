package com.liteworkflow.engine.impl.command;

import com.liteworkflow.engine.impl.Command;
import com.liteworkflow.engine.impl.CommandContext;
import com.liteworkflow.engine.impl.parser.BpmnParser;
import com.liteworkflow.engine.model.BpmnModel;

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
		return BpmnParser.parse(bytes);
	}
}
