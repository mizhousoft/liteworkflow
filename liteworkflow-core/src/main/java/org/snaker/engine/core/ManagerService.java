/* Copyright 2013-2015 www.snakerflow.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.snaker.engine.core;

import java.util.List;

import org.snaker.engine.IManagerService;
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
public class ManagerService extends AccessService implements IManagerService
{
	private SurrogateEntityService surrogateEntityService;

	public void saveOrUpdate(Surrogate surrogate)
	{
		AssertHelper.notNull(surrogate);
		surrogate.setState(STATE_ACTIVE);
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
