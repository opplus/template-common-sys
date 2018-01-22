package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.vigekoo.common.exception.AppException;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.dao.SysConfigDao;
import com.vigekoo.entity.SysConfig;
import com.vigekoo.service.SysConfigService;
import com.vigekoo.modules.sys.redis.SysConfigRedis;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("sysConfigService")
public class SysConfigServiceImpl implements SysConfigService {

	@Autowired
	private SysConfigDao sysConfigDao;

	@Autowired
	private SysConfigRedis sysConfigRedis;
	
	@Override
	@Transactional
	public void save(SysConfig config) {
		sysConfigDao.save(config);
		sysConfigRedis.saveOrUpdate(config);
	}

	@Override
	@Transactional
	public void update(SysConfig config) {
		sysConfigRedis.delete(config);
		sysConfigDao.update(config);
	}

	@Override
	@Transactional
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfig config = queryObject(id);
			sysConfigRedis.delete(config);
		}

		sysConfigDao.deleteBatch(ids);
	}

	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int)map.get("page"), (int)map.get("limit"));
		List<SysConfig> list = this.sysConfigDao.queryList(map);
		PageInfo<SysConfig> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int)map.get("page"), (int)map.get("limit"));
		return pageUtil;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		return sysConfigDao.queryTotal(map);
	}

	@Override
	public SysConfig queryObject(Long id) {
		SysConfig config = sysConfigRedis.get(id);
		if(config == null){
			config = sysConfigDao.queryObject(id);
			sysConfigRedis.saveOrUpdate(config);
		}
		return config;
	}

	@Override
	public String getValue(String key) {
		SysConfig config = sysConfigRedis.get(key);
		if(config == null){
			config = sysConfigDao.queryObjectByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getValue();
	}
	
	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new AppException("获取参数失败");
		}
	}

	@Override
	public SysConfig queryObjectByKey(String key) {
		SysConfig config = sysConfigRedis.get(key);
		if(config == null){
			config = sysConfigDao.queryObjectByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}
		return config;
	}

}
