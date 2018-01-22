package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.dao.SysRoleDao;
import com.vigekoo.entity.SysRole;
import com.vigekoo.service.SysRoleMenuService;
import com.vigekoo.service.SysRoleService;
import com.vigekoo.service.SysUserRoleService;
import com.vigekoo.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {

	@Autowired
	private SysRoleDao sysRoleDao;

	@Autowired
	private SysRoleMenuService sysRoleMenuService;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysUserService sysUserService;

	@Override
	public SysRole queryObject(Long id) {
		return sysRoleDao.queryObject(id);
	}

	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int) map.get("page"), (int) map.get("limit"));
		List<SysRole> list = this.sysRoleDao.queryList(map);
		PageInfo<SysRole> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int) map.get("page"), (int) map.get("limit"));
		return pageUtil;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysRoleDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysRole role) {
		role.setCreateTime(new Date());
		sysRoleDao.save(role);
		//保存角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void update(SysRole role) {
		sysRoleDao.update(role);
		//更新角色与菜单关系
		sysRoleMenuService.saveOrUpdate(role.getId(), role.getMenuIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		sysRoleDao.deleteBatch(ids);
		//删除角色与菜单关系
		sysRoleMenuService.deleteBatch(ids);
	}

}
