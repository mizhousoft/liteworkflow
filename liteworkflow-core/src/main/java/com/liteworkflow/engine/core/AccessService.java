package com.liteworkflow.engine.core;

import com.liteworkflow.engine.Completion;
import com.liteworkflow.engine.impl.GeneralCompletion;

/**
 * 作为抽象父类，提供给子类access实现方式
 * 
 * @author yuqs
 * @since 1.0
 */
public abstract class AccessService
{
	/**
	 * 完成触发接口
	 */
	private Completion completion = null;

	/**
	 * setter
	 * 
	 * @param completion 完成对象
	 */
	public void setCompletion(Completion completion)
	{
		this.completion = completion;
	}

	public Completion getCompletion()
	{
		if (completion != null)
		{
			return completion;
		}

		completion = ServiceContext.find(Completion.class);
		if (completion == null)
		{
			ServiceContext.put(Completion.class.getName(), GeneralCompletion.class);
			completion = ServiceContext.find(Completion.class);
		}
		return completion;
	}
}
