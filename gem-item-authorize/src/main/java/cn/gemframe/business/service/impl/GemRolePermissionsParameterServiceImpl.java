/**
 * @Title:业务实现
 * @Description:角色和权限的关联管理
 * Copyright 2018 GemFrame技术团队 http://www.gemframe.cn
 * Company: DianShiKongJian (Beijing) Technology Co., Ltd.
 * @author Ryan
 * @date 2018-11-1 16:06:06
 * @version V1.0
 *
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package cn.gemframe.business.service.impl;

import cn.gemframe.business.domain.GemRolePermissionsParameter;
import cn.gemframe.config.exception.GemFrameException;
import cn.gemframe.config.exception.status.GemFrameErrorStatus;
import cn.gemframe.config.utils.GemFrameIdUtlis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.gemframe.business.dao.GemRolePermissionsParameterMapper;
import cn.gemframe.business.service.GemRolePermissionsParameterService;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @Title:业务实现
 * @Description:角色和权限的关联管理
 * @author Ryan
 * @date 2018-11-1 16:06:06
 * @version V1.0
 */
@Service
public class GemRolePermissionsParameterServiceImpl implements GemRolePermissionsParameterService {

	@Autowired
	private GemRolePermissionsParameterMapper rolePermissionsParameterMapper;

	/**
	 * @Description:添加角色和参数的关联关系
	 * @param roleId 角色主键集合
	 * @param params 参数主键集合
	 * @param permiss 权限主键
	 * @author: Ryan  
	 * @date 2018年11月13日
	 */
	@Override
	public Integer saveRoleAndParams(Long[] roleId, Long permiss, String[] params) {
		if(roleId!=null && roleId.length>0 && permiss!=null) {
			for (Long rid : roleId) {
				Example example = new Example(GemRolePermissionsParameter.class);
				Criteria createCriteria = example.createCriteria();
				createCriteria.andEqualTo("roleId", rid);
				createCriteria.andEqualTo("permissId", permiss);
				rolePermissionsParameterMapper.deleteByExample(example);
			}
		}else {
			throw new GemFrameException(GemFrameErrorStatus.PARAMETER_ERROR);
		}
		if(params!=null && params.length>0) {
			for (Long rid : roleId) {
				for (String param : params) {
					 String[] split = param.split("_");
					GemRolePermissionsParameter rolePermissionsParameter = new GemRolePermissionsParameter();
					rolePermissionsParameter.setId(GemFrameIdUtlis.Id());
					rolePermissionsParameter.setRoleId(rid);
					rolePermissionsParameter.setPermissId(permiss);
					rolePermissionsParameter.setName(split[0]);
					rolePermissionsParameter.setParamKey(split[1]);
					rolePermissionsParameter.setParamWhere(split[2]);
					rolePermissionsParameter.setParamType(split[3]);
					rolePermissionsParameter.setParamValue(split[4]);
					 rolePermissionsParameterMapper.insert(rolePermissionsParameter);
				}
			}
		}
		return null;
	}

}
