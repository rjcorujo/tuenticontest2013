package org.tuenti.contest.solver;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * User: robertcorujo
 */
public class Dictionary {

    private static final Version LUCENE_VERSION = Version.LUCENE_36;
    private static final String WORD_FIELD = "word";

    private IndexReader reader = null;


    public Dictionary(String dictionary) {
        try {
            String indexName = "lucene_ch07";

            if (!checkExistsIndexDirectoryCreateIfNot(indexName)) {

                File indexDir = new File(indexName);


                Directory directory = FSDirectory.open(indexDir);
                Analyzer analyzer = new StandardAnalyzer(LUCENE_VERSION);
                IndexWriterConfig config = new IndexWriterConfig(LUCENE_VERSION, analyzer);
                IndexWriter writer = new IndexWriter(directory, config);

                populateDictionaryIndex(dictionary,writer);
                writer.close();
            }
        } catch (Exception e) {
            System.out.println(new File(".").getAbsolutePath());
            throw new RuntimeException("Error building dictionary",e);
        }

    }

    private void populateDictionaryIndex(String dictionary, IndexWriter writer) throws IOException {
        BufferedReader bf = new BufferedReader(new FileReader(dictionary));

        String line = bf.readLine();
        while (line != null) {
            String word = line.trim();
            Document doc = buildLuceneDocumentForWord(word);
            writer.addDocument(doc);
            line = bf.readLine();
        }
        writer.commit();
    }

    private Document buildLuceneDocumentForWord(String word) {
        Document doc = new Document();

        doc.add(new Field(WORD_FIELD,word,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
        return doc;
    }

    public boolean anyWordStartsWith(String prefix) {
        PrefixQuery query = new PrefixQuery(new Term(WORD_FIELD,prefix.toUpperCase()));
        return hasQueryResults(query);
    }


    public boolean containsWord(String word) {
        TermQuery query = new TermQuery(new Term(WORD_FIELD,word.toUpperCase()));
        return hasQueryResults(query);
    }


    private boolean hasQueryResults(Query query) {

        IndexSearcher indexSearcher = null;
        try {
            IndexReader indexReader = getIndexReader();
            indexSearcher = new IndexSearcher(indexReader);

            TopScoreDocCollector collector = TopScoreDocCollector.create(1, false);
            indexSearcher.search(query,collector);

            return collector.getTotalHits() > 0;
        } catch (IOException e) {
            throw new RuntimeException("Error searching in lucene",e);
        } finally {
            try {
                if (indexSearcher != null) {
                    indexSearcher.close();
                }
            } catch (IOException e) {}

        }
    }

    private IndexReader getIndexReader() throws IOException {
        if (reader == null) {
            Directory directory = FSDirectory.open(new File("lucene_ch07"));
            reader = IndexReader.open(directory);
        }
        return reader;
    }

    public void close() {
        if (reader != null) {
             try {
                if (reader != null) {
                    reader.close();
                }
             } catch (IOException e) {}
        }
    }


    private boolean checkExistsIndexDirectoryCreateIfNot(String indexName) {
        File file = new File(indexName);
        if (!file.exists()) {
            file.mkdir();
            return false;
        }
        return true;
    }



}
