package com.liteworkflow.engine;

import com.liteworkflow.engine.core.Execution;

/**
 * 决策处理器接口
 * 
 * @author yuqs
 * @since 1.0
 */
public interface DecisionHandler
{
	/**
	 * 定义决策方法，实现类需要根据执行对象做处理，并返回后置流转的name
	 * 
	 * @param execution
	 * @return String 后置流转的name
	 */
	String decide(Execution execution);
}
