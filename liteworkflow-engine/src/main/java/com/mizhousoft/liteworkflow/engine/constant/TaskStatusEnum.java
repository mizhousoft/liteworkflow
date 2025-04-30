package com.mizhousoft.liteworkflow.engine.constant;

/**
 * 任务状态
 *
 * @version
 */
public enum TaskStatusEnum
{
    // 创建
	CREATED("created"),
    // 处理中
	RUNNING("running"),
    // 暂停
	PAUSED("paused"),
    // 取消
	CANCELED("canceled"),
    // 完成
	COMPLETED("completed"),
    // 终止
	TERMINATED("terminated");

	/**
	 * 构造函数
	 *
	 * @param val
	 */
	private TaskStatusEnum(String val)
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
	public static TaskStatusEnum get(String val)
	{
		TaskStatusEnum[] values = TaskStatusEnum.values();
		for (TaskStatusEnum value : values)
		{
			if (value.getValue().equals(val))
			{
				return value;
			}
		}

		return null;
	}
}
