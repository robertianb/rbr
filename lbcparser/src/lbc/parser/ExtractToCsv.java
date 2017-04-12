package lbc.parser;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;

import lbc.beans.OfferItem;
import lbc.beans.Repository;

import org.apache.log4j.Logger;

public class ExtractToCsv {

  
    private static final Logger log = Logger.getLogger(ExtractToCsv.class);
    private static final String SEPARATOR = ";";
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
  
	public static void main(String[] args) throws IOException {
		Repository repository = new Repository();
		repository.load();
		
		BufferedWriter bf = new BufferedWriter(new FileWriter("items.csv"));
		
		bf.append("title" + SEPARATOR
		    + "date"
		    + SEPARATOR + "category\n");
		for (OfferItem item : repository.getItems().values())
		{
		    bf.append(item.getTitle() + SEPARATOR
		        + (item.getCreationDate()!=null?dateFormat.format(item.getCreationDate()):"" ) + SEPARATOR
		        + (item.getCategory()!=null?item.getCategory():"")
		        + "\n");
		}
		
		bf.flush();
		bf.close();
		log.info("Done.");
	}


}
