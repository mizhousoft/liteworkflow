package com.mizhousoft.liteworkflow.bpmn.model;

/**
 * 选择目标类型
 *
 * @version
 */
public enum SelectTargetType
{
    // 发起人自己
	TARGET_INITIATOR("target_initiator"),
    // 部门主管
	TARGET_MANAGER("target_manager"),
    // 直接主管
	TARGET_SUPERVISOR("target_supervisor"),
    // 指定成员
	TARGET_USER("target_user"),
    // 发起人自选
	TARGET_SELECT("target_select");

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private SelectTargetType(String val)
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
	public static SelectTargetType get(String val)
	{
		SelectTargetType[] values = SelectTargetType.values();
		for (SelectTargetType value : values)
		{
			if (value.getValue().equals(val))
			{
				return value;
			}
		}

		return null;
	}
}
