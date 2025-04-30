package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 选择来源
 *
 * @version
 */
public enum SelectFromEnum
{
    // 指定成员
	USERS("users"),
    // 全公司
	ALL("all");

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private SelectFromEnum(String val)
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
	public static SelectFromEnum get(String val)
	{
		SelectFromEnum[] values = SelectFromEnum.values();
		for (SelectFromEnum value : values)
		{
			if (value.getValue().equals(val))
			{
				return value;
			}
		}

		return null;
	}
}
