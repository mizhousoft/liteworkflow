package com.liteworkflow.engine.impl;

import java.util.List;

import com.liteworkflow.engine.persistence.task.entity.TaskActor;

/**
 * 任务访问策略类
 * 用于判断给定的操作人员是否允许执行某个任务
 * 
 * @author yuqs
 * @since 1.4
 */
public interface TaskAccessStrategy
{
	/**
	 * 根据操作人id、参与者集合判断是否允许访问所属任务
	 * 
	 * @param operator 操作人id
	 * @param actors 参与者列表 传递至该接口的实现类中的参与者都是为非空
	 * @return boolean 是否允许访问
	 */
	boolean isAllowed(String operator, List<TaskActor> actors);
}
