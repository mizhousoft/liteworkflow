package com.liteworkflow.engine;

import com.liteworkflow.engine.entity.Surrogate;

/**
 * 管理服务接口,用于流程管理控制服务
 * 委托管理
 * 时限控制
 * 
 * @author yuqs
 * @since 1.4
 */
public interface ManagerService
{
	/**
	 * 保存或更新委托代理对象
	 * 
	 * @param surrogate 委托代理对象
	 */
	public void saveOrUpdate(Surrogate surrogate);

	/**
	 * 删除委托代理对象
	 * 
	 * @param id 委托代理主键id
	 */
	public void deleteSurrogate(String id);

	/**
	 * 根据主键id查询委托代理对象
	 * 
	 * @param id 主键id
	 * @return surrogate 委托代理对象
	 */
	public Surrogate getSurrogate(String id);

	/**
	 * 根据授权人、流程名称获取最终代理人
	 * 如存在user1->user2->user3，那么最终返回user3
	 * 
	 * @param operator 授权人
	 * @param processName 流程名称
	 * @return String 代理人
	 */
	public String getSurrogate(String operator, String processName);
}
