package com.jk.b_indexdao.test;

import com.jk._domain.Article;
import com.jk._domain.PageResult;
import com.jk.b_indexdao.IndexDao;
import com.jk.b_indexdao.IndexDaoImp;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by dell on 2017/6/2.
 */
public class IndexDaoImpTest extends TestCase {

    private IndexDao indexDao = new IndexDaoImp();

    @Test
    public void testSaveArticle() throws Exception {
        //准备数据
        Article article = new Article();
        article.setId(2);
        article.setTitle("顺丰菜鸟之争：国家邮政局连夜介入要顾大局");
        article.setContent("顺丰菜鸟6月1日因谁先关闭数据接入一事而上演“互撕”大战后，国家邮政局连夜出手“压制”，恐影响时下荔枝等农产品的寄送。6月1日夜间，国家邮政局连夜发文《关于近期快递服务消费的提示》称，受今日菜鸟网络与顺丰速运关闭互通数据接口影响，导致少量快件信息查询不畅，时下樱桃、荔枝、杨梅、芒果等生鲜农产品寄递业务会受到一定影响。国家邮政局对此事高度重视，及时与当事双方高层进行沟通，强调要讲政治，顾大局，寻求解决问题的最大公约数，切实维护市场秩序和消费者合法权益，决不能因企业间的纠纷产生严重的社会影响和负面效应。");

        //放到索引库中
        indexDao.saveArticle(article);
    }

    @Test
    public void testSaveArticle2() throws Exception {
        for (int i = 1; i <= 25; i++) {
            //准备数据
            Article article = new Article();
            article.setId(i);
            article.setTitle("顺丰菜鸟之争：国家邮政局连夜介入要顾大局");
            article.setContent("顺丰菜鸟6月1日因谁先关闭数据接入一事而上演“互撕”大战后，国家邮政局连夜出手“压制”，恐影响时下荔枝等农产品的寄送。6月1日夜间，国家邮政局连夜发文《关于近期快递服务消费的提示》称，受今日菜鸟网络与顺丰速运关闭互通数据接口影响，导致少量快件信息查询不畅，时下樱桃、荔枝、杨梅、芒果等生鲜农产品寄递业务会受到一定影响。国家邮政局对此事高度重视，及时与当事双方高层进行沟通，强调要讲政治，顾大局，寻求解决问题的最大公约数，切实维护市场秩序和消费者合法权益，决不能因企业间的纠纷产生严重的社会影响和负面效应。");

            //放到索引库中
            indexDao.saveArticle(article);
        }
    }

    @Test
    public void testDeleteArticle() throws Exception {
        indexDao.deleteArticle(2);
    }

    @Test
    public void testUpdateArticle() throws Exception {
        //准备数据
        Article article = new Article();
        article.setId(1);
        article.setTitle("香格里拉对话开启 就热点问题开展会议引发网友热议");
        article.setContent("2017年度香格里拉对话会(简称香会)今天起在新加坡举行。据《环球时报》记者了解，参加2017年度香会的中方代表团将于2日抵达新加坡，团长是解放军军事科学院副院长何雷中将。而据美国方面消息，美国国防部长马蒂斯、美军参谋长联席会议主席邓福德都将参加香会。\n" +
                "\n" +
                "　　美国《外交学者》网站1日发文称，在香会各种正式或非正式会议中，美国的亚洲政策都将吸引大部分注意力，尤其是在特朗普的亚洲政策尚不明了的情况下，朝鲜问题、南海局势都将是会议的热点问题。据了解，按照惯例，中方会在大会发言后以新闻记者会的形式针对涉及中方言论对外阐述观点。");

        //更新到索引库
        indexDao.updateArticle(article);
    }

    @Test
    public void testSelectArticle() throws Exception {
        //准备查询条件
        String queryString = "国家";

        //执行搜索
//        PageResult pageResult = indexDao.selectArticle(queryString, 0, 10);
//        PageResult pageResult = indexDao.selectArticle(queryString, 10, 10);
        PageResult pageResult = indexDao.selectArticle(queryString, 20, 10);

        for (Article a : (List<Article>) pageResult.getList())
            System.out.println("id:" + a.getId() + " title:" + a.getTitle() + " content:" + a.getContent());
    }


}