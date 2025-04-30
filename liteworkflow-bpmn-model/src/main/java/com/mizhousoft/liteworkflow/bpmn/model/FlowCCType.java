package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 抄送类型
 *
 * @version
 */
public enum FlowCCType
{
    // 指定成员
	TARGET_USER("target_user");

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private FlowCCType(String val)
	{
		this.value = val;
	}

	/**
	 * 值
	 */
	private final String value;

	/**
	 * 获取value
	 * 
	 * @return
	 */
	public String getValue()
	{
		return value;
	}

	public boolean isSelf(String val)
	{
		return (this.value.equals(val));
	}

	/**
	 * 获取状态
	 * 
	 * @param status
	 * @return
	 */
	public static FlowCCType get(String val)
	{
		FlowCCType[] values = FlowCCType.values();
		for (FlowCCType value : values)
		{
			if (value.getValue().equals(val))
			{
				return value;
			}
		}

		return null;
	}
}
