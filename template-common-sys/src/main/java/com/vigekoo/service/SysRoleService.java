package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
	
	SysRole queryObject(Long id);

	PageUtils queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRole role);
	
	void update(SysRole role);
	
	void deleteBatch(Long[] ids);

}
