package com.mizhousoft.liteworkflow.bpmn.model;

import java.util.List;

/**
 * 操作
 *
 */
public class FlowOperation
{
	/**
	 * 是否启用
	 */
	private boolean enable;

	/**
	 * 操作ID
	 */
	private String id;

	/**
	 * 操作文本
	 */
	private String label;

	/**
	 * 审批意见
	 */
	private Remark remark;

	/**
	 * 可回退到的节点
	 */
	private List<FlowElement> backToNodes;

	/**
	 * 获取enable
	 * 
	 * @return
	 */
	public boolean isEnable()
	{
		return enable;
	}

	/**
	 * 设置enable
	 * 
	 * @param enable
	 */
	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}

	/**
	 * 获取id
	 * 
	 * @return
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * 设置id
	 * 
	 * @param id
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * 获取label
	 * 
	 * @return
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * 设置label
	 * 
	 * @param label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * 获取remark
	 * 
	 * @return
	 */
	public Remark getRemark()
	{
		return remark;
	}

	/**
	 * 设置remark
	 * 
	 * @param remark
	 */
	public void setRemark(Remark remark)
	{
		this.remark = remark;
	}

	/**
	 * 获取backToNodes
	 * 
	 * @return
	 */
	public List<FlowElement> getBackToNodes()
	{
		return backToNodes;
	}

	/**
	 * 设置backToNodes
	 * 
	 * @param backToNodes
	 */
	public void setBackToNodes(List<FlowElement> backToNodes)
	{
		this.backToNodes = backToNodes;
	}

	/**
	 * 审批意见
	 *
	 */
	public static class Remark
	{
		/**
		 * 是否启用
		 */
		private boolean enable;

		/**
		 * 是否必须
		 */
		private boolean required;

		/**
		 * 获取enable
		 * 
		 * @return
		 */
		public boolean isEnable()
		{
			return enable;
		}

		/**
		 * 设置enable
		 * 
		 * @param enable
		 */
		public void setEnable(boolean enable)
		{
			this.enable = enable;
		}

		/**
		 * 获取required
		 * 
		 * @return
		 */
		public boolean isRequired()
		{
			return required;
		}

		/**
		 * 设置required
		 * 
		 * @param required
		 */
		public void setRequired(boolean required)
		{
			this.required = required;
		}
	}
}
