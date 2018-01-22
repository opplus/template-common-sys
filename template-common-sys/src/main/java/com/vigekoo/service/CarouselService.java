package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.Carousel;
import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(轮播图)
 * @date 2017-11-30 15:35:54
 */
public interface CarouselService {
	
	Carousel queryObject(Integer id);

	PageUtils queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Carousel carousel);
	
	void update(Carousel carousel);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

}
