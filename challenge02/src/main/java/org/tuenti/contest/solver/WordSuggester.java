package org.tuenti.contest.solver;

import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.tuenti.contest.input.TestCase;

import java.io.*;
import java.util.*;

/**
 * User: robertcorujo
 */
public class WordSuggester {

    private static WordSuggester suggester;
    private static final Version LUCENE_VERSION = Version.LUCENE_36;
    private static final String WORD_LENGTH_FIELD = "length";
    private static final String WORD_FIELD = "word";
    private static final String CHAR_COUNT_FIELD_PREFIX = "count_";

    public static WordSuggester getInstance(String dictionaryName) {
        if (suggester == null) {
            suggester = new WordSuggester(dictionaryName);
        }
        return suggester;
    }

    private WordSuggester(String dictionary) {
        try {
            String indexName = "lucene_"+dictionary;

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
        Map<Character,Integer> characterCounts = computeCharacterCount(word);
        Document doc = new Document();

        doc.add(new Field(WORD_FIELD,word,Field.Store.YES,Field.Index.NOT_ANALYZED_NO_NORMS));
        doc.add(new Field(WORD_LENGTH_FIELD,""+word.length(),Field.Store.NO,Field.Index.NOT_ANALYZED_NO_NORMS));
        for (Character character : characterCounts.keySet()) {
            String fieldName = CHAR_COUNT_FIELD_PREFIX+character;
            doc.add(new Field(fieldName,""+characterCounts.get(character),Field.Store.NO,Field.Index.NOT_ANALYZED_NO_NORMS));
        }
        return doc;
    }

    private Map<Character, Integer> computeCharacterCount(String word) {
        Map<Character,Integer> chars = new HashMap<Character, Integer>();
        for (char character : word.toCharArray()) {
            Integer times = chars.get(character);
            times = times == null ? 1 : times+1;
            chars.put(character,times);
        }
        return chars;
    }

    public LinkedHashMap<String,List<String>> getSuggestions(TestCase input) {
        try {
            Directory directory = FSDirectory.open(new File("lucene_"+input.getDictionaryName()));
            IndexReader indexReader = IndexReader.open(directory);
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);

            LinkedHashMap<String,List<String>> suggestions = new LinkedHashMap<String, List<String>>();
            for (String word : input.getWords()) {
                List<String> wordSuggestions = getSuggestions(word,indexSearcher);
                wordSuggestions.remove(word);
                Collections.sort(wordSuggestions);
                suggestions.put(word,wordSuggestions);
            }

            indexSearcher.close();
            indexReader.close();
            return suggestions;
        } catch (IOException e) {
            throw new RuntimeException("Error searching in lucene",e);
        }
    }

    private List<String> getSuggestions(String word, IndexSearcher searcher) throws IOException {
        Map<Character,Integer> characterCounts = computeCharacterCount(word);
        List<TermQuery> terms = new ArrayList<TermQuery>();

        terms.add(new TermQuery(new Term(WORD_LENGTH_FIELD,""+word.length())));
        for (Character character: characterCounts.keySet()) {
            int count = characterCounts.get(character);
            terms.add(new TermQuery(new Term(CHAR_COUNT_FIELD_PREFIX+character,""+count)));
        }

        TopScoreDocCollector collector = TopScoreDocCollector.create(1000, false);
        searcher.search(buildMustQueryField(terms),collector);

        return buildSuggestionFromSearchResult(collector.topDocs().scoreDocs, searcher);
    }

    private List<String> buildSuggestionFromSearchResult(ScoreDoc[] scoreDocs, IndexSearcher searcher) throws IOException{
        List<String> words = new ArrayList<String>();
        for (ScoreDoc scoreDoc: scoreDocs) {
            Document doc = searcher.doc(scoreDoc.doc);
            String word = doc.get(WORD_FIELD);
            words.add(word);
        }
        return words;
    }

    private BooleanQuery buildMustQueryField(List<TermQuery> termQueries) {
        BooleanQuery bq = new BooleanQuery();
        for (TermQuery termQuery : termQueries) {
            bq.add(new BooleanClause(termQuery, BooleanClause.Occur.MUST));
        }
        return bq;
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
