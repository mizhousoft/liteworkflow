package com.mizhousoft.liteworkflow.engine.domain;

import java.time.LocalDateTime;

/**
 * 流程定义
 *
 * @version
 */
public interface ProcessDefinition
{
	/**
	 * 获取id
	 * 
	 * @return
	 */
	int getId();

	/**
	 * 获取key
	 * 
	 * @return
	 */
	String getKey();

	/**
	 * 获取name
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 获取category
	 * 
	 * @return
	 */
	String getCategory();

	/**
	 * 获取version
	 * 
	 * @return
	 */
	int getVersion();

	/**
	 * 获取description
	 * 
	 * @return
	 */
	String getDescription();

	/**
	 * 获取createTime
	 * 
	 * @return
	 */
	LocalDateTime getCreateTime();

	/**
	 * 获取updateTime
	 * 
	 * @return
	 */
	LocalDateTime getUpdateTime();

	/**
	 * 获取creator
	 * 
	 * @return
	 */
	String getCreator();
}
