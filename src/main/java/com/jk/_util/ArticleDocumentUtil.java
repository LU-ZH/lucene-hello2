package com.jk._util;

import com.jk._domain.Article;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.util.NumericUtils;

/**
 * Created by dell on 2017/6/1.
 */
public class ArticleDocumentUtil {

    /**
     * document转换为article
     * @param document
     * @return
     */
    public static Article documentToArticle(Document document){
        Article article = new Article();
        Integer id = NumericUtils.prefixCodedToInt(document.get("id"));
        article.setId(id);// 一定要用lucene的工具类将id转换为数字
        article.setTitle(document.get("title"));
        article.setContent(document.get("content"));
        return article;
    }

    /**
     * article转换为document
     * @param article
     * @return
     */
    public static Document articleToDocument(Article article){
        String idStr = NumericUtils.intToPrefixCoded(article.getId());
        Document document = new Document();
        document.add(new Field("titile", article.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("content", article.getContent(), Field.Store.YES, Field.Index.ANALYZED));
        document.add(new Field("id", idStr, Field.Store.YES, Field.Index.NOT_ANALYZED));//唯一标识符，一般选择NOT_ANALYZED
        return document;
    }
}
