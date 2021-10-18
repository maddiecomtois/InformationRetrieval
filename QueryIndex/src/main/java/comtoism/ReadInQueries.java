package comtoism;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadInQueries {

	ArrayList<String> queryIds = new ArrayList<String>();
	ArrayList<String> queryText = new ArrayList<String>();

	void processQueriesFile() throws IOException {

		// read in the 225 Cran queries
		BufferedReader br = new BufferedReader(new FileReader("../cran/cran.qry"));
		String line = br.readLine();

		int i = 1;
		while (line != null) {
			// process query id
			if (line.contains(".I")) {
				queryIds.add(Integer.toString(i));
				line = br.readLine();
				i++;
			}
			// process query text
			else if (line.contains(".W")) {
				String query = "";
				line = br.readLine();
				while (line != null && (!line.contains(".I"))) {
					query = query + line + " ";
					line = br.readLine();
				}
				queryText.add(query);

			} else {
				line = br.readLine();
			}

		}
		br.close();
	}
}
