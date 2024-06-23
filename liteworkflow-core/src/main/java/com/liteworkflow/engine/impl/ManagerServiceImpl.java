package com.liteworkflow.engine.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.liteworkflow.engine.Constants;
import com.liteworkflow.engine.ManagerService;
import com.liteworkflow.engine.helper.StringHelper;
import com.liteworkflow.engine.persistence.entity.Surrogate;
import com.liteworkflow.engine.persistence.request.SurrogateFindRequest;
import com.liteworkflow.engine.persistence.service.SurrogateEntityService;
import com.mizhousoft.commons.lang.LocalDateTimeUtils;

/**
 * 管理服务类
 * 
 * @author
 * @since 1.4
 */
public class ManagerServiceImpl extends AccessService implements ManagerService
{
	private SurrogateEntityService surrogateEntityService;

	@Override
	public void saveOrUpdate(Surrogate surrogate)
	{
		surrogate.setState(Constants.STATE_ACTIVE);
		if (StringUtils.isBlank(surrogate.getId()))
		{
			surrogate.setId(StringHelper.getPrimaryKey());
			surrogateEntityService.addEntity(surrogate);
		}
		else
		{
			surrogateEntityService.modifyEntity(surrogate);
		}
	}

	@Override
	public void deleteSurrogate(String id)
	{
		Surrogate surrogate = getSurrogate(id);

		surrogateEntityService.deleteEntity(surrogate);
	}

	@Override
	public Surrogate getSurrogate(String id)
	{
		return surrogateEntityService.getById(id);
	}

	@Override
	public String getSurrogate(String operator, String processName)
	{
		SurrogateFindRequest request = new SurrogateFindRequest();
		request.setOperators(new String[] { operator });
		request.setOperateTime(LocalDateTimeUtils.formatYmdhms(LocalDateTime.now()));
		if (!StringUtils.isBlank(processName))
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
