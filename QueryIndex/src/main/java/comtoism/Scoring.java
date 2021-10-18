package comtoism;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.index.DirectoryReader;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.IndexSearcher;

public class Scoring {

	Analyzer analyzer;
	Directory directory;
	DirectoryReader directoryReader;
	IndexSearcher indexSearcher;
	QueryParser parser;
	ArrayList<String> queryIds;
	ArrayList<String> queryText;

	public Scoring(Analyzer a, ArrayList<String> ids, ArrayList<String> text) throws IOException {

		// Analyzer to be used by the query parser.
		Analyzer analyzer = a;

		// Create the query parser using the specified document fields and analyzer
		String[] fields = { "docText", "docTitle", "docAuthors", "docBibliography" };
		parser = new MultiFieldQueryParser(fields, analyzer);

		// Load in the 225 Cran queries
		queryIds = ids;
		queryText = text;

	}

	/*
	 * Score the queries using the index searcher's top hits
	 */
	void scoreBooleanMode(String indexDirectory, String resultsDirectory) throws IOException, ParseException {

		// Open the corresponding Custom index
		directory = FSDirectory.open(Paths.get(indexDirectory));
		directoryReader = DirectoryReader.open(directory);
		indexSearcher = new IndexSearcher(directoryReader);
		indexSearcher.setSimilarity(new BooleanSimilarity());

		// Create a results file
		FileWriter fw = new FileWriter(resultsDirectory);

		// Loop through each query and rank the documents based on relevance
		for (int i = 0; i < queryText.size(); i++) {
			// clean up query syntax
			String queryString = "";
			queryString = queryText.get(i);
			queryString = queryString.replaceAll("\\p{Punct}", "").toLowerCase().trim();

			Query query = parser.parse(queryString);

			// Get the 1400 results ranked from most to least relevant
			ScoreDoc[] hits = indexSearcher.search(query, 1400).scoreDocs;

			System.out.println("Scoring Boolean similarity query: " + queryIds.get(i));

			// Write the query hits to a result file to be evaluated with trec_eval
			for (int j = 0; j < hits.length; j++) {
				Document hitDoc = indexSearcher.doc(hits[j].doc);
				fw.write(queryIds.get(i) + " Q0 " + hitDoc.get("docId") + " " + (j + 1) + " " + hits[j].score + " BOOL\n");
			}

		}

		directoryReader.close();
		directory.close();
		fw.close();
	}

	/*
	 * Score the queries using the index searcher's top hits along with BM25
	 * Similarity
	 */
	void scoreBM25Mode(String indexDirectory, String resultsDirectory) throws IOException, ParseException {

		// Open the corresponding BM25 index
		directory = FSDirectory.open(Paths.get(indexDirectory));
		directoryReader = DirectoryReader.open(directory);
		indexSearcher = new IndexSearcher(directoryReader);
		indexSearcher.setSimilarity(new BM25Similarity());

		// Create a results file
		FileWriter fw = new FileWriter(resultsDirectory);

		// Loop through each query and rank the documents based on relevance
		for (int i = 0; i < queryText.size(); i++) {
			// clean up query syntax
			String queryString = "";
			queryString = queryText.get(i);
			queryString = queryString.replaceAll("\\p{Punct}", "").toLowerCase().trim();

			Query query = parser.parse(queryString);

			// Get the 1400 results ranked from most to least relevant
			ScoreDoc[] hits = indexSearcher.search(query, 1400).scoreDocs;

			System.out.println("Scoring BM25 similarity query: " + queryIds.get(i));

			// Write the query hits to a result file to be evaluated with trec_eval
			for (int k = 0; k < hits.length; k++) {
				Document hitDoc = indexSearcher.doc(hits[k].doc);
				fw.write(queryIds.get(i) + " Q0 " + hitDoc.get("docId") + " " + (k + 1) + " " + hits[k].score + " BM25\n");
			}

		}

		directoryReader.close();
		directory.close();
		fw.close();
	}

	/*
	 * Score the queries using the index searcher's top hits along with VSM
	 * Similarity
	 */
	void scoreVSMMode(String indexDirectory, String resultsDirectory) throws IOException, ParseException {

		// Open the corresponding VSM index
		directory = FSDirectory.open(Paths.get(indexDirectory));
		directoryReader = DirectoryReader.open(directory);
		indexSearcher = new IndexSearcher(directoryReader);
		indexSearcher.setSimilarity(new ClassicSimilarity());

		// Create a results file
		FileWriter fw = new FileWriter(resultsDirectory);

		// Loop through each query and rank the documents based on relevance
		for (int i = 0; i < queryText.size(); i++) {
			// clean up query syntax
			String queryString = "";
			queryString = queryText.get(i);
			queryString = queryString.replaceAll("\\p{Punct}", "").toLowerCase().trim();

			Query query = parser.parse(queryString);

			// Get the 1400 results ranked from most to least relevant
			ScoreDoc[] hits = indexSearcher.search(query, 1400).scoreDocs;

			System.out.println("Scoring VSM similarity query: " + queryIds.get(i));

			// Write the query hits to a result file to be evaluated with trec_eval
			for (int k = 0; k < hits.length; k++) {
				Document hitDoc = indexSearcher.doc(hits[k].doc);
				fw.write(queryIds.get(i) + " Q0 " + hitDoc.get("docId") + " " + (k + 1) + " " + hits[k].score + " VSM\n");
			}

		}

		directoryReader.close();
		directory.close();
		fw.close();
	}

}
