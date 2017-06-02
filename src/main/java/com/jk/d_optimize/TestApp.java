package com.jk.d_optimize;

import com.jk._domain.Article;
import com.jk._util.LuceneUtil;
import com.jk.b_indexdao.IndexDaoImp;
import org.junit.Test;

/**
 * Created by dell on 2017/6/2.
 */
public class TestApp {

    //优化索引库文件（合并多个小文件，成为一个大文件）
    @Test
    public void test() throws Exception{
        LuceneUtil.getIndexWriter().optimize();
    }

    //自动合并文件
    @Test
    public void testAuto() throws Exception{
        //配置当小文件的数量达到多少个后就自动合并为一个大文件，默认为10，最小为2
        LuceneUtil.getIndexWriter().setMergeFactor(5);

        //建立索引
        Article article = new Article();
        article.setId(26);
        article.setTitle("准备Lucene的开发环境");
        article.setContent("sda;lkfja;dslkjf;ldsakjf;ladskjf;lajdsklfdjslaghk");
        new IndexDaoImp().saveArticle(article);
    }
}
