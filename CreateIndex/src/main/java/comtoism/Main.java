package comtoism;

import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;


public class Main {
	
    public static void main(String[] args) throws IOException
    {
    	
    	// Process the data from the Cran data set and store in ArrayLists
		ProcessCran processCran = new ProcessCran();
    	processCran.readInData();
    	
		// Analyzers used by the query parser.
    	Analyzer customAnalyzer = CustomAnalyzer.builder()
  		      .withTokenizer("standard")
  		      .addTokenFilter("lowercase")
  		      .addTokenFilter("stop")
  		      .addTokenFilter("porterstem")
  		      .addTokenFilter("englishMinimalStem")
  		      .addTokenFilter("englishPossessive")
  		      .build();
  		      
    	
    	Analyzer standardAnalyzer = new StandardAnalyzer();
    	
    	Analyzer englishAnalyzer = new EnglishAnalyzer();
    	
    	// create an instance of the indexer class
    	IndexCran indexer = new IndexCran(processCran.docIds, processCran.docTitles, processCran.docAuthors, processCran.docBibliography,processCran.docText);
    	
   
    	/*
    	 * Custom analyzer
    	 */
    	// No similarity analysis
    	indexer.setConfig(customAnalyzer, "../indexes/customBooleanIndex", "BOOL");
    	indexer.createIndex();
    	
    	// BM25 similarity analysis
    	indexer.setConfig(customAnalyzer, "../indexes/customBM25Index", "BM25");
    	indexer.createIndex();
    	
    	// VSM similarity analysis
    	indexer.setConfig(customAnalyzer, "../indexes/customVSMIndex", "VSM");
    	indexer.createIndex();
 
    	/*
    	 * Standard Analyzer
    	 */
    	// No similarity analysis
    	indexer.setConfig(standardAnalyzer, "../indexes/standardBooleanIndex", "BOOL");
    	indexer.createIndex();
    	
    	// BM25 similarity analysis
    	indexer.setConfig(standardAnalyzer, "../indexes/standardBM25Index", "BM25");
    	indexer.createIndex();
    	
    	// VSM similarity analysis
    	indexer.setConfig(standardAnalyzer, "../indexes/standardVSMIndex", "VSM");
    	indexer.createIndex();
    	
    	/*
    	 * English Analyzer
    	 */
    	// No similarity analysis
    	indexer.setConfig(englishAnalyzer, "../indexes/englishBooleanIndex", "BOOL");
    	indexer.createIndex();
    	
    	// BM25 similarity analysis
    	indexer.setConfig(englishAnalyzer, "../indexes/englishBM25Index", "BM25");
    	indexer.createIndex();
    	
    	// VSM similarity analysis
    	indexer.setConfig(englishAnalyzer, "../indexes/englishVSMIndex", "VSM");
    	indexer.createIndex();
    }
}
