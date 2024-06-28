package com.liteworkflow.engine.model;

/**
 * 活动元素
 * 
 * @author
 * @since 1.0
 */
public abstract class ActivityModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 761102386160546149L;

	/**
	 * form
	 */
	private String form;

	/**
	 * 获取form
	 * 
	 * @return
	 */
	public String getForm()
	{
		return form;
	}

	/**
	 * 设置form
	 * 
	 * @param form
	 */
	public void setForm(String form)
	{
		this.form = form;
	}
}
