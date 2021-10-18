package comtoism;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessCran {

	ArrayList<String> docIds = new ArrayList<String>();
	ArrayList<String> docTitles = new ArrayList<String>();
	ArrayList<String> docAuthors = new ArrayList<String>();
	ArrayList<String> docBibliography = new ArrayList<String>();
	ArrayList<String> docText = new ArrayList<String>();

	/*
	 * This function uses a BufferedReader to read in the Cran data set. Each
	 * section of the data (id, title, author, etc.) is separated into a
	 * corresponding ArrayList
	 */
	void readInData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("../cran/cran.all.1400"));
		String line = br.readLine();

		while (line != null) {
			// process Ids
			if (line.contains(".I")) {
				docIds.add(line.substring(3));
				line = br.readLine();
			}
			// process titles
			else if (line.contains(".T")) {
				String titleText = "";
				line = br.readLine();
				while ((!line.contains(".A"))) {
					titleText = titleText + line + " ";
					line = br.readLine();
				}
				docTitles.add(titleText);
			}
			// process authors
			else if (line.contains(".A")) {
				String authorText = "";
				line = br.readLine();
				while ((!line.contains(".B"))) {
					authorText = authorText + line;
					line = br.readLine();
				}
				docAuthors.add(authorText);

			}
			// process bibliography
			else if (line.contains(".B")) {
				String bibText = "";
				line = br.readLine();
				while ((!line.contains(".W"))) {
					bibText = bibText + line;
					line = br.readLine();
				}
				docBibliography.add(bibText);

			}
			// process text
			else if (line.contains(".W")) {
				String documentText = "";
				line = br.readLine();
				while (line != null && (!line.contains(".I"))) {
					documentText = documentText + line + " ";
					line = br.readLine();
				}
				docText.add(documentText);

			} else {
				line = br.readLine();
			}

		}
		br.close();

	}

}
