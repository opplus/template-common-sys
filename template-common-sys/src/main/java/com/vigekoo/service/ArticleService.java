package com.vigekoo.service;

import com.vigekoo.common.utils.PageUtils;
import com.vigekoo.entity.Article;
import java.util.List;
import java.util.Map;

/**
 * @author oplus
 * @Description: TODO(文章)
 * @date 2017-11-30 15:35:54
 */
public interface ArticleService {
	
	Article queryObject(Integer id);

	PageUtils queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(Article article);
	
	void update(Article article);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);

}
