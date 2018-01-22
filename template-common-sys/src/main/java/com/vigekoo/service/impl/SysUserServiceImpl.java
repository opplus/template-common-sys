package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.Constant;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.dao.SysMenuDao;
import com.vigekoo.dao.SysUserDao;
import com.vigekoo.entity.SysMenu;
import com.vigekoo.entity.SysUser;
import com.vigekoo.modules.sys.redis.SysUserRedis;
import com.vigekoo.service.SysRoleService;
import com.vigekoo.service.SysUserRoleService;
import com.vigekoo.service.SysUserService;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

	@Autowired
	private SysUserDao sysUserDao;

	@Autowired
	private SysUserRoleService sysUserRoleService;

	@Autowired
	private SysRoleService sysRoleService;

	@Autowired
	private SysMenuDao sysMenuDao;

	@Autowired
	private SysUserRedis sysUserRedis;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserDao.queryAllPerms(userId);
	}

	@Override
	public List<Long> queryAllMenuId(Long userId) {
		return sysUserDao.queryAllMenuId(userId);
	}

	@Override
	public SysUser queryByUserName(String username) {
		SysUser sysUser=sysUserRedis.get(username);
		if(sysUser==null){
			sysUser=sysUserDao.queryByUserName(username);
			sysUserRedis.saveOrUpdate(sysUser);
		}
		return sysUser;
	}

	@Override
	public SysUser queryObject(Long id) {
		SysUser sysUser=sysUserRedis.get(id);
		if(sysUser==null){
			sysUser=sysUserDao.queryObject(id);
			sysUserRedis.saveOrUpdate(sysUser);
		}
		return sysUser;
	}

	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int) map.get("page"), (int) map.get("limit"));
		List<SysUser> list = this.sysUserDao.queryList(map);
		PageInfo<SysUser> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int) map.get("page"), (int) map.get("limit"));
		return pageUtil;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysUserDao.queryTotal(map);
	}

	@Override
	@Transactional
	public void save(SysUser user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserDao.save(user);

		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());

		sysUserRedis.saveOrUpdate(user);
	}

	@Override
	@Transactional
	public void update(SysUser user) {
		sysUserRedis.delete(user);

		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		sysUserDao.update(user);
		
		//保存用户与角色关系
		sysUserRoleService.saveOrUpdate(user.getId(), user.getRoleIdList());
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysUser sysUser=queryObject(id);
			sysUserRedis.delete(sysUser);
		}

		sysUserDao.deleteBatch(ids);

		//删除用户与角色关系
		sysUserRoleService.deleteBatch(ids);
	}

	@Override
	@Transactional
	public int updatePassword(SysUser user, String password, String newPassword) {
		sysUserRedis.delete(user);

		Map<String, Object> map = new HashMap<>();
		map.put("id", user.getId());
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserDao.updatePassword(map);
	}

	@Override
	public Set<String> getUserPermissions(long userId) {
		List<String> permsList;

		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			List<SysMenu> menuList = sysMenuDao.queryList(new HashMap<>());
			permsList = new ArrayList<>(menuList.size());
			for(SysMenu menu : menuList){
				permsList.add(menu.getPerms());
			}
		}else{
			permsList = sysUserDao.queryAllPerms(userId);
		}
		//用户权限列表
		Set<String> permsSet = new HashSet<>();
		for(String perms : permsList){
			if(StringUtils.isBlank(perms)){
				continue;
			}
			permsSet.addAll(Arrays.asList(perms.trim().split(",")));
		}
		return permsSet;
	}

}
