package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.dao.SysLogDao;
import com.vigekoo.service.SysLogService;
import com.vigekoo.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("sysLogService")
public class SysLogServiceImpl implements SysLogService {

	@Autowired
	private SysLogDao sysLogDao;
	
	@Override
	public SysLog queryObject(Long id){
		return sysLogDao.queryObject(id);
	}


	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int)map.get("page"), (int)map.get("limit"));
		List<SysLog> list = this.sysLogDao.queryList(map);
		PageInfo<SysLog> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int)map.get("page"), (int)map.get("limit"));
		return pageUtil;
	}

	@Override
	public int queryTotal(Map<String, Object> map){
		return sysLogDao.queryTotal(map);
	}
	
	@Override
	public void save(SysLog sysLog){
		sysLogDao.save(sysLog);
	}
	
	@Override
	public void delete(Long id){
		sysLogDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Long[] ids){
		sysLogDao.deleteBatch(ids);
	}
	
}
