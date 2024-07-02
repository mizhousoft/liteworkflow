package com.liteworkflow.engine;

import java.io.InputStream;
import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.ProcessDefinitionPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义业务类
 * 
 * @author
 * @since 1.0
 */
public interface RepositoryService
{
	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param input
	 * @return
	 */
	String deploy(InputStream input);

	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param input
	 * @param creator
	 * @return
	 */
	String deploy(InputStream input, String creator);

	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param id
	 * @param input
	 */
	void redeploy(String id, InputStream input);

	/**
	 * 删除流程定义
	 * 
	 * @param id
	 * @param cascade
	 */
	void deleteDeployment(String id, boolean cascade);

	/**
	 * 根据主键ID获取流程定义对象
	 * 
	 * @param id
	 * @return
	 */
	ProcessDefinition getById(String id);

	/**
	 * 根据流程name获取流程定义对象
	 * 
	 * @param name
	 * @return
	 */
	ProcessDefinition getLatestByName(String name);

	/**
	 * 根据流程name、version获取流程定义对象
	 * 
	 * @param name
	 * @param version
	 * @return
	 */
	ProcessDefinition getByVersion(String name, Integer version);

	/**
	 * 根据名称查询
	 * 
	 * @param name
	 * @return
	 */
	List<ProcessDefinition> queryListByName(String name);

	/**
	 * 分页查询数据
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessDefinition> queryPageData(ProcessDefinitionPageRequest request);
}
