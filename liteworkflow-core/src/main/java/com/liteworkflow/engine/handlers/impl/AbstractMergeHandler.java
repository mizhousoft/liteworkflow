package com.liteworkflow.engine.handlers.impl;

import java.util.List;

import com.liteworkflow.engine.OrderService;
import com.liteworkflow.engine.TaskService;
import com.liteworkflow.engine.core.Execution;
import com.liteworkflow.engine.handlers.IHandler;
import com.liteworkflow.engine.model.ProcessModel;
import com.liteworkflow.engine.model.SubProcessModel;
import com.liteworkflow.engine.model.TaskModel;
import com.liteworkflow.order.entity.Order;
import com.liteworkflow.order.request.OrderPageRequest;
import com.liteworkflow.task.entity.Task;
import com.liteworkflow.task.request.TaskPageRequest;

/**
 * 合并处理的抽象处理器
 * 需要子类提供查询无法合并的task集合的参数map
 * 
 * @author yuqs
 * @since 1.0
 */
public abstract class AbstractMergeHandler implements IHandler
{
	public void handle(Execution execution)
	{
		/**
		 * 查询当前流程实例的无法参与合并的node列表
		 * 若所有中间node都完成，则设置为已合并状态，告诉model可继续执行join的输出变迁
		 */
		OrderService orderService = execution.getEngine().getOrderService();
		TaskService taskService = execution.getEngine().getTaskService();

		Order order = execution.getOrder();
		ProcessModel model = execution.getModel();
		String[] activeNodes = findActiveNodes();
		boolean isSubProcessMerged = false;
		boolean isTaskMerged = false;

		if (model.containsNodeNames(SubProcessModel.class, activeNodes))
		{
			OrderPageRequest request = new OrderPageRequest();
			request.setParentId(order.getId());
			request.setExcludedIds(new String[] { execution.getChildOrderId() });
			List<Order> orders = orderService.getActiveOrders(request);
			// 如果所有子流程都已完成，则表示可合并
			if (orders == null || orders.isEmpty())
			{
				isSubProcessMerged = true;
			}
		}
		else
		{
			isSubProcessMerged = true;
		}
		if (isSubProcessMerged && model.containsNodeNames(TaskModel.class, activeNodes))
		{
			TaskPageRequest request = new TaskPageRequest();
			request.setOrderId(order.getId());
			request.setExcludedIds(new String[] { execution.getTask().getId() });
			request.setNames(activeNodes);

			List<Task> tasks = taskService.getActiveTasks(request);
			if (tasks == null || tasks.isEmpty())
			{
				// 如果所有task都已完成，则表示可合并
				isTaskMerged = true;
			}
		}
		execution.setMerged(isSubProcessMerged && isTaskMerged);
	}

	/**
	 * 子类需要提供如何查询未合并任务的参数map
	 * 
	 * @return
	 */
	protected abstract String[] findActiveNodes();
}
