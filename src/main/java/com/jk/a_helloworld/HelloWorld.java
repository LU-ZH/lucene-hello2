package com.jk.a_helloworld;

import com.jk._domain.Article;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/5/31.
 */
public class HelloWorld {

    private static Directory directory;//索引库目录
    private static Analyzer analyzer;//分词器

    static {
        try {
            directory = FSDirectory.open(new File("./indexDir"));
            analyzer = new StandardAnalyzer(Version.LUCENE_30);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //建立索引
    @Test
    public void testCreateIndex() throws Exception{
        //准备数据
        Article article = new Article();
        article.setId(1);
        article.setTitle("美国军方确认邀请中国第三次参加环太军演");
        article.setContent("【环球时报综合报道】“紧张局势下，美国再邀中国参加环太军演”，据美国防务新闻网29日报道，美国海军方面当天确认，已经向中国方面发出了参加2018年“环太平洋”联合军事演习的邀请。");

        //放到索引库中
        //1.把Artical转为Document
        Document doc = new Document();
        doc.add(new Field("id",article.getId().toString(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("title",article.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("content",article.getContent(), Field.Store.YES, Field.Index.ANALYZED));

        //2.把Document放到索引库中
        IndexWriter indexWriter = new IndexWriter(directory,analyzer, IndexWriter.MaxFieldLength.LIMITED);
        indexWriter.addDocument(doc);
        indexWriter.close();
    }

    //搜索
    @Test
    public void testSearch() throws Exception {
        //准备查询条件
        String queryString = "美国";

        //执行搜索
        List<Article> list = new ArrayList<Article>();

        //----------------------------------------------------

        //1.把查询字符串转为Query对象(默认只从title中查询)
        QueryParser queryParser = new QueryParser(Version.LUCENE_30,"title",analyzer);
        Query query = queryParser.parse(queryString);

        //2.执行查询，得到结果
        IndexSearcher indexSearcher = new IndexSearcher(directory);//指定所用的索引库
        TopDocs topDocs = indexSearcher.search(query,100);//最多返回前100条数据
        
        int count = topDocs.totalHits;
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        
        //3.处理结果
        for(ScoreDoc s: scoreDocs){
            float score = s.score;//相关度得分
            int docId = s.doc;//Document的内部编号
            
            //根据编号拿到Document数据
            Document doc = indexSearcher.doc(docId);
            
            //把Document转为Article
            String idStr = doc.get("id");//等价于doc.getField("id").stringValue()
            String title = doc.get("title");
            String content = doc.get("content");

            Article article = new Article();
            article.setId(Integer.valueOf(idStr));
            article.setTitle(title);
            article.setContent(content);

            list.add(article);
        }
        indexSearcher.close();

        //----------------------------------------------------

        //显示结果
        for (Article a: list){
            System.out.println("id:"+a.getId()+",title:"+a.getTitle()+",content:"+a.getContent());
        }
    }
}
