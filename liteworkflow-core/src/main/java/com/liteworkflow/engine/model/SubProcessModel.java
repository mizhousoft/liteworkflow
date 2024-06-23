package com.liteworkflow.engine.model;

/**
 * 子流程定义subprocess元素
 * 
 * @author
 * @since 1.0
 */
public class SubProcessModel extends WorkModel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3923955459202018147L;

	/**
	 * 子流程名称
	 */
	private String processName;

	/**
	 * 子流程版本号
	 */
	private Integer version;

	/**
	 * 子流程定义引用
	 */
	private ProcessModel subProcess;

	/**
	 * 获取processName
	 * 
	 * @return
	 */
	public String getProcessName()
	{
		return processName;
	}

	/**
	 * 设置processName
	 * 
	 * @param processName
	 */
	public void setProcessName(String processName)
	{
		this.processName = processName;
	}

	/**
	 * 获取version
	 * 
	 * @return
	 */
	public Integer getVersion()
	{
		return version;
	}

	/**
	 * 设置version
	 * 
	 * @param version
	 */
	public void setVersion(Integer version)
	{
		this.version = version;
	}

	/**
	 * 获取subProcess
	 * 
	 * @return
	 */
	public ProcessModel getSubProcess()
	{
		return subProcess;
	}

	/**
	 * 设置subProcess
	 * 
	 * @param subProcess
	 */
	public void setSubProcess(ProcessModel subProcess)
	{
		this.subProcess = subProcess;
	}

}
