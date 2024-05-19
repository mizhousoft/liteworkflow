package com.liteworkflow.boot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.liteworkflow.process.service.ProcessEntityService;

/**
 * WeixinPayListProperties Test
 *
 * @version
 */
@SpringBootTest(classes = DemoApplication.class)
public class WeixinPropertiesTest
{
	@Autowired
	private ProcessEntityService processEntityService;

	@Test
	public void test()
	{
		processEntityService.getLatestProcessVersion("s");
	}
}
