package comtoism;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;

public class IndexCran {

	ArrayList<String> docIds;
	ArrayList<String> docTitles;
	ArrayList<String> docAuthors;
	ArrayList<String> docBibliography;
	ArrayList<String> docText;
	Analyzer analyzer;
	Directory directory;
	IndexWriterConfig config;
	String configMode;

	/*
	 * The constructor defines the different field values read in from the Cran
	 * documents that are used for indexing
	 */
	public IndexCran(ArrayList<String> ids, ArrayList<String> titles, ArrayList<String> authors, ArrayList<String> bib,
			ArrayList<String> text) {
		docIds = ids;
		docTitles = titles;
		docAuthors = authors;
		docBibliography = bib;
		docText = text;
	}

	/*
	 * This function sets up the analyzer and similarity configuration (if used) for
	 * creating an index
	 */
	void setConfig(Analyzer a, String indexDirectory, String cM) throws IOException {
		// Analyzer that is used to process the documents
		analyzer = a;

		// Directory to store the index (one level above)
		directory = FSDirectory.open(Paths.get(indexDirectory));
		config = new IndexWriterConfig(analyzer);
		config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		configMode = cM;

		// Set the search similarity for later search scoring
		if (configMode.equalsIgnoreCase("BM25")) {
			config.setSimilarity(new BM25Similarity());
		}

		if (configMode.equalsIgnoreCase("VSM")) {
			config.setSimilarity(new ClassicSimilarity());

		}
		
		if (configMode.equalsIgnoreCase("BOOL")) {
			config.setSimilarity(new BooleanSimilarity());

		}
		System.out.println("\n\nConfigurations set for analyzer with " + configMode + " similarity analysis\n\n");

	}

	/*
	 * This function creates an index based on the values defined in the setConfig() method
	 * function
	 */
	void createIndex() throws IOException {

		// Create a new field type which will store term vector information
		FieldType ft = new FieldType(TextField.TYPE_STORED);
		ft.setTokenized(true);
		ft.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
		ft.setStoreTermVectors(true);
		ft.setStoreTermVectorPositions(true);
		ft.setStoreTermVectorOffsets(true);
		ft.setStoreTermVectorPayloads(true);

		IndexWriter indexWriter = new IndexWriter(directory, config);

		// ArrayList to store the documents to be indexed
		ArrayList<Document> documents = new ArrayList<Document>();

		// Loop through each entry in the Cran data set arrays and create a document to
		// add to the index
		for (int i = 0; i < docIds.size(); i++) {
			System.out.printf("Indexing with %s Similarity Document: \"%s\"\n", configMode, docIds.get(i));

			Document doc = new Document();
			doc.add(new Field("docId", docIds.get(i), ft));
			doc.add(new Field("docTitle", docTitles.get(i), ft));
			doc.add(new Field("docAuthors", docAuthors.get(i), ft));
			doc.add(new Field("docBibliography", docBibliography.get(i), ft));
			doc.add(new Field("docText", docText.get(i), ft));

			documents.add(doc);
		}

		// Add the documents to the index
		indexWriter.addDocuments(documents);
		indexWriter.close();
		directory.close();

	}

}
