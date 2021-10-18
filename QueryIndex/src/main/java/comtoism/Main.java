package comtoism;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.custom.CustomAnalyzer;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;


public class Main {
	
    public static void main(String[] args) throws IOException, ParseException
    {
    	
		// Custom analyzer for the query parser.		
    	Analyzer customAnalyzer = CustomAnalyzer.builder()
  		      .withTokenizer("standard")
  		      .addTokenFilter("lowercase")
  		      .addTokenFilter("stop")
  		      .addTokenFilter("porterstem")
  		      .addTokenFilter("englishMinimalStem")
  		      .addTokenFilter("englishPossessive")
  		      .build();
    	
    	// Standard analyzer for the query parser
    	Analyzer standardAnalyzer = new StandardAnalyzer();
    	
    	// English analyzer for the query parser
    	Analyzer englishAnalyzer = new EnglishAnalyzer();
    	
    	// Read in the query data
    	ReadInQueries processor = new ReadInQueries();
    	processor.processQueriesFile();
    	
    	// score the queries against the Custom analyzer
    	System.out.println("\nScoring with Custom analyzer\n");
    	Scoring score = new Scoring(customAnalyzer, processor.queryIds, processor.queryText);
    	score.scoreBooleanMode("../indexes/customBooleanIndex", "../results/qrelsCustomBoolean.results");
    	score.scoreBM25Mode("../indexes/customBM25Index", "../results/qrelsCustomBM25.results");
    	score.scoreVSMMode("../indexes/customVSMIndex", "../results/qrelsCustomVSM.results");
    	
    	// score the queries against the Standard analyzer
    	System.out.println("\nScoring with Standard analyzer\n");
    	Scoring score2 = new Scoring(standardAnalyzer, processor.queryIds, processor.queryText);
    	score2.scoreBooleanMode("../indexes/standardBooleanIndex", "../results/qrelsStandardBoolean.results");
    	score2.scoreBM25Mode("../indexes/standardBM25Index", "../results/qrelsStandardBM25.results");
    	score2.scoreVSMMode("../indexes/standardVSMIndex", "../results/qrelsStandardVSM.results");
    	
    	// score the queries against the English analyzer
    	System.out.println("\nScoring with English analyzer\n");
    	Scoring score3 = new Scoring(englishAnalyzer, processor.queryIds, processor.queryText);
    	score3.scoreBooleanMode("../indexes/englishBooleanIndex", "../results/qrelsEnglishBoolean.results");
    	score3.scoreBM25Mode("../indexes/englishBM25Index", "../results/qrelsEnglishBM25.results");
    	score3.scoreVSMMode("../indexes/englishVSMIndex", "../results/qrelsEnglishVSM.results");
    }
}
