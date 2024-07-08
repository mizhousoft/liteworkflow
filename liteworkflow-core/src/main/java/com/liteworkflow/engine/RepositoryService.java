package com.liteworkflow.engine;

import java.io.InputStream;
import java.util.List;

import com.liteworkflow.engine.persistence.entity.ProcessDefinition;
import com.liteworkflow.engine.persistence.request.ProcessDefinitionPageRequest;
import com.mizhousoft.commons.data.domain.Page;

/**
 * 流程定义业务类
 * 
 * @version
 */
public interface RepositoryService
{
	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param istream
	 * @return
	 */
	String deploy(InputStream istream);

	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param istream
	 * @param creator
	 * @return
	 */
	String deploy(InputStream istream, String creator);

	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param processDefinitionId
	 * @param istream
	 */
	void redeploy(String processDefinitionId, InputStream istream);

	/**
	 * 删除流程定义
	 * 
	 * @param processDefinitionId
	 * @param cascade
	 */
	void deleteDeployment(String processDefinitionId, boolean cascade);

	/**
	 * 根据ID获取流程定义对象
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	ProcessDefinition getProcessDefinition(String processDefinitionId);

	/**
	 * 根据流程定义Key获取流程定义对象
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	ProcessDefinition getLatestByKey(String processDefinitionKey);

	/**
	 * 根据流程定义processDefinitionKey、version获取流程定义对象
	 * 
	 * @param processDefinitionKey
	 * @param version
	 * @return
	 */
	ProcessDefinition getByVersion(String processDefinitionKey, Integer version);

	/**
	 * 根据流程定义Key查询
	 * 
	 * @param processDefinitionKey
	 * @return
	 */
	List<ProcessDefinition> queryList(String processDefinitionKey);

	/**
	 * 分页查询数据
	 * 
	 * @param request
	 * @return
	 */
	Page<ProcessDefinition> queryPageData(ProcessDefinitionPageRequest request);
}
