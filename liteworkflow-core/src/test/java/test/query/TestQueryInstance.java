package test.query;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.request.ProcessInstancePageRequest;

import test.TestSpring;

/**
 * 流程实例查询测试
 * 
 * @author
 * @since 1.0
 */
public class TestQueryInstance extends TestSpring
{
	@BeforeEach
	public void before()
	{
		engine = applicationContext.getBean(ProcessEngine.class);
		repositoryService = engine.getRepositoryService();
	}

	@Test
	public void test()
	{
		ProcessInstancePageRequest request = new ProcessInstancePageRequest();
		request.setStartTime(LocalDateTime.of(2014, 1, 1, 0, 0));
		request.setProcessDefinitionId(1);

		System.out.println(engine.getProcessInstanceService().queryPageData(request));

		System.out.println(engine.getProcessInstanceService().queryPageData(new ProcessInstancePageRequest()));
		System.out.println(engine.getProcessInstanceService().getInstance(1));
	}
}
