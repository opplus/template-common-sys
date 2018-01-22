package com.vigekoo.dao;

import com.vigekoo.entity.Article;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ArticleDao extends BaseDao<Article> {
	
}
