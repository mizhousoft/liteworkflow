package com.liteworkflow.engine.core;

import java.util.List;

import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.helper.DateHelper;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.task.entity.Surrogate;
import com.liteworkflow.task.request.SurrogateFindRequest;
import com.liteworkflow.task.service.SurrogateEntityService;

/**
 * 管理服务类
 * 
 * @author yuqs
 * @since 1.4
 */
public class ManagerServiceImpl extends AccessService implements ManagerService
{
	private SurrogateEntityService surrogateEntityService;

	public void saveOrUpdate(Surrogate surrogate)
	{
		surrogate.setState(Constants.STATE_ACTIVE);
		if (StringHelper.isEmpty(surrogate.getId()))
		{
			surrogate.setId(StringHelper.getPrimaryKey());
			surrogateEntityService.save(surrogate);
		}
		else
		{
			surrogateEntityService.update(surrogate);
		}
	}

	public void deleteSurrogate(String id)
	{
		Surrogate surrogate = getSurrogate(id);

		surrogateEntityService.delete(surrogate);
	}

	public Surrogate getSurrogate(String id)
	{
		return surrogateEntityService.getSurrogate(id);
	}

	public String getSurrogate(String operator, String processName)
	{
		SurrogateFindRequest request = new SurrogateFindRequest();
		request.setOperators(new String[] { operator });
		request.setOperateTime(DateHelper.getTime());
		if (StringHelper.isNotEmpty(processName))
		{
			request.setNames(new String[] { processName });
		}

		List<Surrogate> surrogates = surrogateEntityService.queryList(request);
		if (surrogates == null || surrogates.isEmpty())
			return operator;
		StringBuffer buffer = new StringBuffer(50);
		for (Surrogate surrogate : surrogates)
		{
			String result = getSurrogate(surrogate.getSurrogate(), processName);
			buffer.append(result).append(",");
		}
		buffer.deleteCharAt(buffer.length() - 1);
		return buffer.toString();
	}

	/**
	 * 设置surrogateEntityService
	 * 
	 * @param surrogateEntityService
	 */
	public void setSurrogateEntityService(SurrogateEntityService surrogateEntityService)
	{
		this.surrogateEntityService = surrogateEntityService;
	}
}
