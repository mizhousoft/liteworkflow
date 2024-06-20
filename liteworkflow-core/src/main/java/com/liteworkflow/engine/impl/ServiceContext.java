package com.liteworkflow.engine.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.helper.AssertHelper;

/**
 * 单实例的服务上下文
 * 具体的上下文查找服务交给Context的实现类
 * 
 * @author
 * @since 1.5
 */
public abstract class ServiceContext
{
	private static final Logger log = LoggerFactory.getLogger(ServiceContext.class);

	/**
	 * 上下文接口服务{@link Context}
	 */
	private static Context context;

	/**
	 * 流程引擎的引用
	 */
	private static ProcessEngine engine;

	/**
	 * 获取Context实现类
	 * 
	 * @return
	 */
	public static Context getContext()
	{
		return context;
	}

	/**
	 * 设置Context实现类
	 * 
	 * @param context
	 */
	public static void setContext(Context context)
	{
		ServiceContext.context = context;
	}

	/**
	 * 获取注册的引擎实例
	 * 
	 * @return
	 */
	public static ProcessEngine getEngine()
	{
		AssertHelper.notNull(context, "未注册服务上下文");
		if (engine == null)
		{
			engine = context.find(ProcessEngine.class);
		}
		return engine;
	}

	/**
	 * 向上下文添加服务实例
	 * 
	 * @param name 服务名称
	 * @param object 服务实例
	 */
	public static void put(String name, Object object)
	{
		AssertHelper.notNull(context, "未注册服务上下文");

		log.info("put new instance[name=" + name + "][object=" + object + "]");

		context.put(name, object);
	}

	/**
	 * 向上下文添加服务实例
	 * 
	 * @param name 服务名称
	 * @param clazz 服务类型
	 */
	public static void put(String name, Class<?> clazz)
	{
		AssertHelper.notNull(context, "未注册服务上下文");

		log.info("put new instance[name=" + name + "][clazz=" + clazz.getName() + "]");

		context.put(name, clazz);
	}

	/**
	 * 根据服务名称判断是否存在服务实例
	 * 
	 * @param name 服务名称
	 * @return
	 */
	public static boolean exist(String name)
	{
		AssertHelper.notNull(context, "未注册服务上下文");
		return context.exist(name);
	}

	/**
	 * 对外部提供的查找对象方法，根据class类型查找
	 * 
	 * @param clazz 服务类型
	 * @return
	 */
	public static <T> T find(Class<T> clazz)
	{
		AssertHelper.notNull(context, "未注册服务上下文");
		return context.find(clazz);
	}

	/**
	 * 对外部提供的查找对象实例列表方法，根据class类型查找集合
	 * 
	 * @param clazz 服务类型
	 * @return
	 */
	public static <T> List<T> findList(Class<T> clazz)
	{
		AssertHelper.notNull(context, "未注册服务上下文");
		return context.findList(clazz);
	}

	/**
	 * 对外部提供的查找对象方法，根据名称、class类型查找
	 * 
	 * @param name 服务名称
	 * @param clazz 服务类型
	 * @return
	 */
	public static <T> T findByName(String name, Class<T> clazz)
	{
		AssertHelper.notNull(context, "未注册服务上下文");
		return context.findByName(name, clazz);
	}
}
