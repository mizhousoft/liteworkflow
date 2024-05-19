package com.liteworkflow.task.service;

import java.util.List;

import com.liteworkflow.task.entity.Surrogate;
import com.liteworkflow.task.request.SurrogateFindRequest;

/**
 * SurrogateEntityService
 *
 * @version
 */
public interface SurrogateEntityService
{
	/**
	 * 保存委托代理对象
	 * 
	 * @param surrogate 委托代理对象
	 */
	void save(Surrogate surrogate);

	/**
	 * 更新委托代理对象
	 * 
	 * @param surrogate 委托代理对象
	 */
	void update(Surrogate surrogate);

	/**
	 * 删除委托代理对象
	 * 
	 * @param surrogate 委托代理对象
	 */
	void delete(Surrogate surrogate);

	/**
	 * 根据主键id查询委托代理对象
	 * 
	 * @param id 主键id
	 * @return surrogate 委托代理对象
	 */
	Surrogate getSurrogate(String id);

	/**
	 * 
	 * 
	 * @param request
	 * @return
	 */
	List<Surrogate> queryList(SurrogateFindRequest request);
}
