package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.vigekoo.dao.GeneralsDao;
import com.vigekoo.entity.Generals;
import com.vigekoo.service.GeneralsService;

@Service("generalsService")
public class GeneralsServiceImpl implements GeneralsService {

	@Autowired
	private GeneralsDao generalsDao;
	
	@Override
	public Generals queryObject(Integer id){
		return generalsDao.queryObject(id);
	}
	
	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int)map.get("page"), (int)map.get("limit"));
		List<Generals> list = this.generalsDao.queryList(map);
		PageInfo<Generals> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int)map.get("page"), (int)map.get("limit"));
		return pageUtil;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return generalsDao.queryTotal(map);
	}
	
	@Override
	public void save(Generals generals){
		generalsDao.save(generals);
	}
	
	@Override
	public void update(Generals generals){
		generalsDao.update(generals);
	}
	
	@Override
	public void delete(Integer id){
		generalsDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		generalsDao.deleteBatch(ids);
	}
	
}
