package com.liteworkflow.engine.model;

/**
 * 工作元素
 * 
 * @author yuqs
 * @since 1.0
 */
public abstract class WorkModel extends NodeModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 761102386160546149L;

	/**
	 * form
	 */
	private String form;

	public String getForm()
	{
		return form;
	}

	public void setForm(String form)
	{
		this.form = form;
	}
}
