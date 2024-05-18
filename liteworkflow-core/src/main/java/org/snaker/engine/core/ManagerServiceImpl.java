package org.snaker.engine.core;

import java.util.List;

import org.snaker.engine.Constants;
import org.snaker.engine.ManagerService;
import org.snaker.engine.entity.Surrogate;
import org.snaker.engine.helper.AssertHelper;
import org.snaker.engine.helper.DateHelper;
import org.snaker.engine.helper.StringHelper;
import org.snaker.engine.request.SurrogateFindRequest;
import org.snaker.engine.service.SurrogateEntityService;

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
		AssertHelper.notNull(surrogate);
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
		AssertHelper.notNull(surrogate);
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
