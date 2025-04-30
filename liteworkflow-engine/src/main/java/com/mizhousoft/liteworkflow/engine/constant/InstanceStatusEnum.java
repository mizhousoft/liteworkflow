package com.mizhousoft.liteworkflow.engine.constant;

/**
 * 实例状态
 *
 * @version
 */
public enum InstanceStatusEnum
{
    // 处理中
	RUNNING("running"),
    // 审批完成
	COMPLETED("completed"),
    // 已撤销
	TERMINATED("terminated");

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private InstanceStatusEnum(String val)
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
	public static InstanceStatusEnum get(String val)
	{
		InstanceStatusEnum[] values = InstanceStatusEnum.values();
		for (InstanceStatusEnum value : values)
		{
			if (value.getValue().equals(val))
			{
				return value;
			}
		}

		return null;
	}
}
