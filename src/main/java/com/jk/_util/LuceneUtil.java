package com.jk._util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by dell on 2017/6/1.
 */
public class LuceneUtil {

    private static Directory directory;
    private static Analyzer analyzer;
    private static IndexWriter indexWriter;

    public static IndexWriter getIndexWriter() {
        //在第一次使用时进行初始化
        if (indexWriter == null) {
            synchronized (LuceneUtil.class) {//注意线程安全问题
                if (indexWriter == null) {
                    try {
                        indexWriter = new IndexWriter(directory, analyzer, IndexWriter.MaxFieldLength.LIMITED);
                        System.out.println("------已经初始化IndexWriter------");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    try {
                        indexWriter.close();
                        System.out.println("------已经关闭IndexWriter------");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
        return indexWriter;
    }

    static {
        try {
            directory = FSDirectory.open(new File("./indexDir"));
            analyzer = new StandardAnalyzer(Version.LUCENE_30);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Directory getDirectory() {
        return directory;
    }

    public static Analyzer getAnalyzer() {
        return analyzer;
    }
}
