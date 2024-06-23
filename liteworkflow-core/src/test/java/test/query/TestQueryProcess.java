package test.query;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.liteworkflow.engine.ProcessEngine;
import com.liteworkflow.engine.persistence.entity.ProcessDefinition;

import test.TestSpring;

/**
 * 流程定义查询测试
 * 
 * @author
 * @since 1.0
 */
public class TestQueryProcess extends TestSpring
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
		List<ProcessDefinition> list = engine.getRepositoryService().queryByName("subprocess1");
		System.out.println(list.size());

		ProcessDefinition process = engine.getRepositoryService().getProcessByVersion("subprocess1", 0);
		System.out.println(process);

		process = engine.getRepositoryService().getProcessByName("subprocess1");
		System.out.println(process);
	}
}
