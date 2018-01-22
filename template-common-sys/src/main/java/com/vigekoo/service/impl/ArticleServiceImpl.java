package com.vigekoo.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.SysLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import com.vigekoo.dao.ArticleDao;
import com.vigekoo.entity.Article;
import com.vigekoo.service.ArticleService;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Override
	public Article queryObject(Integer id){
		return articleDao.queryObject(id);
	}
	
	@Override
	public PageUtils queryList(Map<String, Object> map) {
		PageHelper.startPage((int)map.get("page"), (int)map.get("limit"));
		List<Article> list = this.articleDao.queryList(map);
		PageInfo<Article> page = new PageInfo(list);
		PageUtils pageUtil = new PageUtils(list, page.getTotal(), (int)map.get("page"), (int)map.get("limit"));
		return pageUtil;
	}
	
	@Override
	public int queryTotal(Map<String, Object> map){
		return articleDao.queryTotal(map);
	}
	
	@Override
	public void save(Article article){
		articleDao.save(article);
	}
	
	@Override
	public void update(Article article){
		articleDao.update(article);
	}
	
	@Override
	public void delete(Integer id){
		articleDao.delete(id);
	}
	
	@Override
	public void deleteBatch(Integer[] ids){
		articleDao.deleteBatch(ids);
	}
	
}
