package com.jk.b_indexdao;

import com.jk._domain.Article;
import com.jk._domain.PageResult;

import java.util.List;

/**
 * Created by dell on 2017/6/1.
 */
public interface IndexDao {

    void saveArticle(Article article);

    void deleteArticle(Integer id);

    void updateArticle(Article article);

    PageResult selectArticle(String s, Integer first, Integer max) throws Exception;

}
