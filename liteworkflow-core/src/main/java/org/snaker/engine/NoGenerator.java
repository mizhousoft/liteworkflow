package org.snaker.engine;

import org.snaker.engine.model.ProcessModel;

/**
 * 编号生成器接口
 * 流程实例的编号字段使用该接口实现类来产生对应的编号
 * 
 * @author yuqs
 * @since 1.0
 */
public interface NoGenerator
{
	/**
	 * 生成器方法
	 * 
	 * @param model
	 * @return String 编号
	 */
	String generate(ProcessModel model);
}
