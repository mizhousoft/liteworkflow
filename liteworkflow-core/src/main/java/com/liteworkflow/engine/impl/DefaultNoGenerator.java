package com.liteworkflow.engine.impl;

import java.time.LocalDateTime;
import java.util.Random;

import com.liteworkflow.engine.NoGenerator;
import com.liteworkflow.engine.model.ProcessModel;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

/**
 * 默认的流程实例编号生成器
 * 编号生成规则为:yyyyMMdd-HH:mm:ss-SSS-random
 * 
 * @author yuqs
 * @since 1.0
 */
public class DefaultNoGenerator implements NoGenerator
{
	public String generate(ProcessModel model)
	{
		String time = LocalDateTimeUtils.format(LocalDateTime.now(), "yyyyMMdd-HH:mm:ss-SSS");
		Random random = new Random();
		return time + "-" + random.nextInt(1000);
	}
}
