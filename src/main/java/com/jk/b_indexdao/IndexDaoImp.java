package com.jk.b_indexdao;

import com.jk._domain.Article;
import com.jk._domain.PageResult;
import com.jk._util.ArticleDocumentUtil;
import com.jk._util.LuceneUtil;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.NumericUtils;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/6/1.
 */

public class IndexDaoImp implements IndexDao {
    public void saveArticle(Article article) {
        //1.把Article转为Document
        Document document = ArticleDocumentUtil.articleToDocument(article);

        //2.添加到索引库中
        IndexWriter indexWriter = null;
        try {
            indexWriter = new IndexWriter(LuceneUtil.getDirectory(), LuceneUtil.getAnalyzer(), IndexWriter.MaxFieldLength.LIMITED);
            indexWriter.addDocument(document);//添加
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭IndexWriter
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 删除索引
     * <p/>
     * term：某字段中出现的某一个关键词（在索引库的目录中）
     *
     * @param id
     */
    public void deleteArticle(Integer id) {
        IndexWriter indexWriter = null;
        try {
            String idStr = NumericUtils.intToPrefixCoded(id);
            Term term = new Term("id", idStr);//一定要使用工具类将数字转为字符串
            indexWriter = new IndexWriter(LuceneUtil.getDirectory(), LuceneUtil.getAnalyzer(), IndexWriter.MaxFieldLength.LIMITED);
            indexWriter.deleteDocuments(term);//删除所有含有这个term的document
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭IndexWriter
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void updateArticle(Article article) {
        IndexWriter indexWriter = null;
        try {
            Term term = new Term("id", NumericUtils.intToPrefixCoded(article.getId()));//一定要使用工具类将数字转为字符串
            Document doc = ArticleDocumentUtil.articleToDocument(article);

            indexWriter = new IndexWriter(LuceneUtil.getDirectory(), LuceneUtil.getAnalyzer(), IndexWriter.MaxFieldLength.LIMITED);
            indexWriter.updateDocument(term, doc);// 更新就是先参数再添加
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭IndexWriter
            if (indexWriter != null) {
                try {
                    indexWriter.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 搜索
     *
     * @param s     查询条件
     * @param first 从结果列表的那个索引开始获取数据
     * @param max   最多获取多少数据（如果没有这么多，就把剩余的都返回）
     * @return 一段数据 + 符合条件的总记录数
     */
    public PageResult selectArticle(String s, Integer first, Integer max) {
        IndexSearcher indexSearcher = null;
        try {
            // 1.把查询字符串转为Query对象（在title与content中查询）
            QueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_30, new String[]{"titile", "content"}, LuceneUtil.getAnalyzer());
            Query query = queryParser.parse(s);

            // 2.执行查询，得到和中间结果

            indexSearcher = new IndexSearcher(LuceneUtil.getDirectory());
            TopDocs topDocs = indexSearcher.search(query, first + max);// 最多返回前n条数据,这里要计算好,要返回够用的数据
            int count = topDocs.totalHits;

            // 3.处理数据并返回
            List<Article> list = new ArrayList<Article>();
            int endIndex = Math.min(first + max, topDocs.scoreDocs.length);//计算结束的边界
            for (int i = first; i < endIndex; i++) {
                //根据内部编号获取真正的Document数据
                int docId = topDocs.scoreDocs[i].doc;
                Document doc = indexSearcher.doc(docId);
                //把Document转换为Article
                Article article = ArticleDocumentUtil.documentToArticle(doc);
                list.add(article);
            }
            return new PageResult(count, list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            //关闭IndexSearcher
            if (indexSearcher != null) {
                try {
                    indexSearcher.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
