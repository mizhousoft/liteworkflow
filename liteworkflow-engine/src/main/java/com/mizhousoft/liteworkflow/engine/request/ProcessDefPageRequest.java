package com.mizhousoft.liteworkflow.engine.request;

import org.apache.commons.lang3.StringUtils;

import com.mizhousoft.commons.data.domain.PageRequest;
import com.mizhousoft.commons.data.domain.Sort;
import com.mizhousoft.commons.data.domain.Sort.Direction;
import com.mizhousoft.commons.web.Normalizer;

/**
 * 分页请求
 *
 * @version
 */
public class ProcessDefPageRequest extends PageRequest implements Normalizer
{
	private static final long serialVersionUID = -4195498843400032234L;

	/**
	 * 流程分类
	 */
	private String category;

	/**
	 * 构造函数
	 *
	 */
	public ProcessDefPageRequest()
	{
		super();

		this.setSort(Sort.create(Direction.ASC, "name"));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void normalize()
	{
		category = StringUtils.trimToNull(category);
	}

	/**
	 * 获取category
	 * 
	 * @return
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * 设置category
	 * 
	 * @param category
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}
}
