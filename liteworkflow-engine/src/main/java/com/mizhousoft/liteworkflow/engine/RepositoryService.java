package com.mizhousoft.liteworkflow.engine;

import java.io.InputStream;
import java.util.List;

import com.mizhousoft.commons.data.domain.Page;
import com.mizhousoft.liteworkflow.bpmn.model.BpmnModel;
import com.mizhousoft.liteworkflow.engine.domain.DeployOption;
import com.mizhousoft.liteworkflow.engine.domain.ProcessDefinition;
import com.mizhousoft.liteworkflow.engine.request.ProcessDefPageRequest;

/**
 * 流程定义业务类
 * 
 * @version
 */
public interface RepositoryService
{
	/**
	 * 根据InputStream输入流，部署流程定义
	 * 
	 * @param istream
	 * @param creator
	 * @param deployOption
	 * @return
	 */
	ProcessDefinition deploy(InputStream istream, String creator, DeployOption deployOption);

	/**
	 * 根据XML字符串，部署流程定义
	 * 
	 * @param xmlString
	 * @param creator
	 * @param deployOption
	 * @return
	 */
	ProcessDefinition deploy(String xmlString, String creator, DeployOption deployOption);

	/**
	 * 根据InputStream輸入流，部署流程定义
	 * 
	 * @param processDefinitionId
	 * @param istream
	 */
	ProcessDefinition redeploy(int processDefinitionId, InputStream istream);

	/**
	 * 重新部署流程定义
	 * 
	 * @param processDefinitionId
	 * @param xmlString
	 */
	ProcessDefinition redeploy(int processDefinitionId, String xmlString);

	/**
	 * 删除流程定义
	 * 
	 * @param processDefinitionId
	 * @param cascade
	 */
	void deleteDeployment(int processDefinitionId, boolean cascade);

	/**
	 * 获取模型
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	BpmnModel getBpmnModel(int processDefinitionId);

	/**
	 * 获取模型字符串
	 * 
	 * @return
	 */
	String getBpmnXMLString(int processDefinitionId);

	/**
	 * 根据ID获取流程定义对象
	 * 
	 * @param processDefinitionId
	 * @return
	 */
	ProcessDefinition getProcessDefinition(int processDefinitionId);

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
	Page<ProcessDefinition> queryPageData(ProcessDefPageRequest request);

	/**
	 * 查询最新的列表
	 * 
	 * @return
	 */
	List<ProcessDefinition> queryLatestList();
}
