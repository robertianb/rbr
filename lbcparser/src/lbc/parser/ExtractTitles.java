package lbc.parser;

import java.io.IOException;

import lbc.beans.OfferItem;
import lbc.beans.Repository;

public class ExtractTitles {

	public static void main(String[] args) throws IOException {
		Repository repository = new Repository();
		repository.loadRecent();
		
		for (OfferItem item : repository.getItems().values())
		{
			System.out.println(item.getTitle());
		}
	}


}
