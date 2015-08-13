package lbc.parser;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lbc.beans.OfferItem;
import lbc.beans.Repository;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main {
	private static final boolean DEBUG = false;
	private static final int MAX_ITERATIONS = 200;
	static Random random = new Random();
	static Calendar calendar = Calendar.getInstance();
	
	private static SimpleDateFormat dayOnlyDateFormat = new SimpleDateFormat("yyyyMMdd");
    private static Pattern idPattern = Pattern.compile("([0-9]+)\\.htm");
    private static Pattern pricePattern = Pattern.compile("[0-9]+");

	public static void main(String[] args) throws IOException {
		Repository repository = new Repository();
		repository.loadRecent();

		try {
			parseDoc(
					"http://www.leboncoin.fr/annonces/offres/pays_de_la_loire/loire_atlantique/?f=a&th=1",
					repository, 0);
		} finally {
			repository.persist("items." + dayOnlyDateFormat.format(new Date())
			    + ".json");
		}
	}

	private static void parseDoc(String url, Repository repository,
			int iterations) throws IOException {
		Document doc;
		if (DEBUG) {
			doc = Jsoup.parse(new File("lbc-test.html"), "iso-8859-1");
		} else {
			int delay = random.nextInt(5000) + 1093;
			try {
				System.out.println("Delaying for " + (delay / 1000) + " s");
				Thread.sleep(delay);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			doc = Jsoup.connect(url).get();
		}

		Elements links = doc.select("div.list-lbc"); // a with href
		boolean added = true;
		for (Element link : links.first().select("a[href]")) {
			OfferItem item = new OfferItem();
			Elements dateDiv = link.select("div.date");
			Elements dateDivs = dateDiv.first().children();
			String dateString = dateDivs.first().text();
			String timeString = dateDivs.get(1).text();
			
			if ("aujourd'hui".equalsIgnoreCase(dateString))
			{
				String[] split = timeString.split(":");
				calendar.setTime(new Date());
				calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
				calendar.set(Calendar.MINUTE, Integer.valueOf(split[1]));
				item.setCreationDate(calendar.getTime());
			} else if ("hier".equalsIgnoreCase(dateString))
			{
				String[] split = timeString.split(":");
				calendar.setTime(new Date());
				calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
				calendar.set(Calendar.MINUTE, Integer.valueOf(split[1]));
				calendar.add(Calendar.DAY_OF_MONTH, -1);
				item.setCreationDate(calendar.getTime());
			} else
			{
				try {
					String[] split = timeString.split(":");
					Date parsedDate = dayOnlyDateFormat.parse(dateString);
					calendar.setTime(parsedDate);
					calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
					calendar.set(Calendar.MINUTE, Integer.valueOf(split[1]));
					item.setCreationDate(calendar.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
			}
			String title = link.attr("title").toString();

			Elements priceDiv = link.select("div.price");
			String price = priceDiv.text();

			Elements categoryDiv = link.select("div.category");
			String categoryString = categoryDiv.text();

			Elements placementDiv = link.select("div.placement");
			String placementString = placementDiv.text();

			item.setCategory(categoryString);
			item.setTitle(title);
			String href = link.attr("href");
			System.out.println("href" + href);
			
			item.setUrl(href);
			
			
			Matcher idMatcher = idPattern.matcher(href);
			if (idMatcher.find())
			{
			  item.setId(idMatcher.group(1));
			}
			item.setPlaceName(placementString);
			if (price != null) {
				
				Matcher matcher = pricePattern.matcher(price);
				if (matcher.find()) {

					String priceString = matcher.group(0);
					try {
						if (!priceString.isEmpty()) {

							Float priceFloat = Float.valueOf(priceString);
							item.setPrice(priceFloat);
						}
					} catch (Exception e) {
						//
						System.out.println("Failed to parse ");
						e.printStackTrace();
					}
				}

			}
			added = repository.addItem(item);
			if (!added) {
				System.out.println("Item already existed : " + item);
				break;
			}
			// System.out.println(" " + dateString + " at " + timeString +
			System.out.println(" " + title + "( " + categoryString + ")");
			// + " @ " + price + "( " + categoryString + ")");
		}

		if (!added) {
			System.out.println("End");
			return;
		}

		Elements nav = doc.select("nav"); // a with href
		for (Element link : nav.get(3).select("a[href]")
				.select(":contains(Page suivante)")) {
			System.out.println("Nav link " + link);
			iterations++;
			if (iterations < MAX_ITERATIONS) {
				parseDoc(link.attr("href"), repository, iterations);
			} else {
				System.out.println("Max number of iterations reached, stopping...");
			}
		}

	}

}
