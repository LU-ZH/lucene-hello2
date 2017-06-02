package com.jk._util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
