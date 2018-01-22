package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.Generals;
import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(兵器)
 * @date 2017-12-19 13:59:34
 */
public interface GeneralsService {
	
	Generals queryObject(Integer id);

	PageUtils queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Generals generals);
	
	void update(Generals generals);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

}
