package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.utils.PageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.vigekoo.dao.CarouselDao;
import com.vigekoo.entity.Carousel;
import com.vigekoo.service.CarouselService;

@Service("carouselService")
public class CarouselServiceImpl implements CarouselService {

	@Autowired
	private CarouselDao carouselDao;
	
	@Override
	public Carousel queryObject(Integer id){
		return carouselDao.queryObject(id);
	}
	
	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int)map.get("page"), (int)map.get("limit"));
		List<Carousel> list = this.carouselDao.queryList(map);
		PageInfo<Carousel> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int)map.get("page"), (int)map.get("limit"));
		return pageUtil;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return carouselDao.queryTotal(map);
	}
	
	@Override
	public void save(Carousel carousel){
		carouselDao.save(carousel);
	}
	
	@Override
	public void update(Carousel carousel){
		carouselDao.update(carousel);
	}
	
	@Override
	public void delete(Integer id){
		carouselDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		carouselDao.deleteBatch(ids);
	}
	
}
