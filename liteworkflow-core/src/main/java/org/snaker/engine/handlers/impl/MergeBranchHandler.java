package org.snaker.engine.handlers.impl;

import java.util.List;

import org.snaker.engine.model.ForkModel;
import org.snaker.engine.model.JoinModel;
import org.snaker.engine.model.NodeModel;
import org.snaker.engine.model.TransitionModel;
import org.snaker.engine.model.WorkModel;

/**
 * 合并分支操作的处理器
 * 
 * @author yuqs
 * @since 1.0
 */
public class MergeBranchHandler extends AbstractMergeHandler
{
	private JoinModel model;

	public MergeBranchHandler(JoinModel model)
	{
		this.model = model;
	}

	/**
	 * 对join节点的所有输入变迁进行递归，查找join至fork节点的所有中间task元素
	 * 
	 * @param node
	 * @param buffer
	 */
	private void findForkTaskNames(NodeModel node, StringBuilder buffer)
	{
		if (node instanceof ForkModel)
			return;
		List<TransitionModel> inputs = node.getInputs();
		for (TransitionModel tm : inputs)
		{
			if (tm.getSource() instanceof WorkModel)
			{
				buffer.append(tm.getSource().getName()).append(",");
			}
			findForkTaskNames(tm.getSource(), buffer);
		}
	}

	/**
	 * 对join节点的所有输入变迁进行递归，查找join至fork节点的所有中间task元素
	 * 
	 * @see org.snaker.engine.handlers.impl.AbstractMergeHandler#findActiveNodes()
	 */
	protected String[] findActiveNodes()
	{
		StringBuilder buffer = new StringBuilder(20);
		findForkTaskNames(model, buffer);
		String[] taskNames = buffer.toString().split(",");
		return taskNames;
	}
}
