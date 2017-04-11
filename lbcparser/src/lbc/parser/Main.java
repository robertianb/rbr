package lbc.parser;

import java.io.File;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lbc.beans.OfferItem;
import lbc.beans.Repository;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Main
{
  private static final int DELAY_MINIMUM = 500;

  private static final int DELAY = 2000;

  private static final boolean DEBUG = false;

  private static final int MAX_ITERATIONS = 2;

  private static final Logger log = Logger.getLogger(Main.class);

  static Random random = new Random();

  static Calendar calendar = Calendar.getInstance();

  private static SimpleDateFormat dayOnlyDateFormat = new SimpleDateFormat("yyyyMMdd");

  private static Pattern idPattern = Pattern.compile("([0-9]+)\\.htm");

  private static Pattern pricePattern = Pattern.compile("[0-9]+");

  public static void main(String[] args)
      throws IOException, InterruptedException
  {
    
    System.setProperty("javax.net.ssl.trustStore", "./leboncoin.fr.jks");
    
    
    Repository repository = new Repository();
    repository.loadRecent();

    try {
      ParseBatch batch = new ParseBatch(repository, "https://www.leboncoin.fr/annonces/offres/pays_de_la_loire/loire_atlantique/?f=a&th=1", 0, 0);
      do {
        batch = parseDoc(batch);
      }
      while (batch.nextURL != null && batch.nbNewItems > 0 && batch.nbIterations < MAX_ITERATIONS);
      log.info("Made " + batch.nbIterations + "iteration(s). Found " + batch.nbNewItems + " new items");
    }
    finally {
      repository.persist("items." + dayOnlyDateFormat.format(new Date()) + ".json");
    }
  }

  private static ParseBatch parseDoc(ParseBatch batch)
      throws IOException, InterruptedException
  {
    String url = batch.nextURL;
    Repository repository = batch.repository;
    batch.nbIterations++;

    Document doc = null;
    if (DEBUG) {
      doc = Jsoup.parse(new File("lbc-test2.htm"), "iso-8859-1");
    }
    else {
      int delay = random.nextInt(DELAY) + DELAY_MINIMUM;
      try {
        System.out.println("Delaying for " + (delay / 1000) + " s");
        Thread.sleep(delay);
      }
      catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      int nbTries = 0;
      boolean success = false;
      do {
        try {
          doc = Jsoup.connect(url).get();
          success = true;
        }
        catch (SocketTimeoutException e) {
          Thread.sleep(DELAY);
        }
      }
      while (nbTries < 5 && !success);
    }

    if (doc == null) {
      batch.nextURL = null;
      return batch;
    }

    Elements links = doc.select("section[id=listingAds]");
    boolean added = true;
    for (Element link1 : links) {
      for (Element link : link1.select("a[href]")) {
        try {
          OfferItem item = new OfferItem();
          Elements dateDiv = link.select("p[itemprop=availabilityStarts]");
          if (dateDiv != null && !dateDiv.isEmpty()) {

            String dateString = dateDiv.attr("content");
            String timeString = dateDiv.text();
            timeString = timeString.split(",")[1].trim();

            if ("aujourd'hui".equalsIgnoreCase(dateString)) {
              String[] split = timeString.split(":");
              calendar.setTime(new Date());
              calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
              calendar.set(Calendar.MINUTE, Integer.valueOf(split[1]));
              item.setCreationDate(calendar.getTime());
            }
            else if ("hier".equalsIgnoreCase(dateString)) {
              String[] split = timeString.split(":");
              calendar.setTime(new Date());
              calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
              calendar.set(Calendar.MINUTE, Integer.valueOf(split[1]));
              calendar.add(Calendar.DAY_OF_MONTH, -1);
              item.setCreationDate(calendar.getTime());
            }
            else {
              try {
                String[] split = timeString.split(":");
                Date parsedDate = dayOnlyDateFormat.parse(dateString);
                calendar.setTime(parsedDate);
                calendar.set(Calendar.HOUR_OF_DAY, Integer.valueOf(split[0]));
                calendar.set(Calendar.MINUTE, Integer.valueOf(split[1]));
                item.setCreationDate(calendar.getTime());
              }
              catch (ParseException e) {
                e.printStackTrace();
              }

            }
          }
          String title = link.select("h2.item_title").text().toString();

          Elements priceDiv = link.select("h3[itemprop=price]");
          String price = priceDiv.attr("content");

          String categoryString = "";
          Elements categoryDiv = link.select("p[itemprop=category]");
          if (categoryDiv != null)
            categoryString = categoryDiv.text();

          String placementString = "";
          Elements placementDiv = link.select("p[itemprop=availableAtOrFrom]");
          if (placementDiv != null)
            placementString = placementDiv.text();

          item.setCategory(categoryString);
          item.setTitle(title);
          String href = link.attr("href");
          if (href.startsWith("//"))
          { 
            href = "http:" + href;
          }
                              // System.out.println("href" + href);

          item.setUrl(href);

          Matcher idMatcher = idPattern.matcher(href);
          if (idMatcher.find()) {
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
              }
              catch (Exception e) {
                //
                System.out.println("Failed to parse ");
                e.printStackTrace();
              }
            }

          }
          if (title == null || title.isEmpty()) {
            // nothing
            added = true;
          }
          else if (DEBUG) {
            log.info("Item to add (DEBUG) : " + item);
            added = true;
          }
          else {
            added = repository.addItem(item);
          }

          if (!added) {
            if (title == null || title.isEmpty()) {
              // nothing
            }
            else {
              log.info("Item already existed : " + item);
              batch.nextURL = null;
            }

            break;
          }
          else {
            batch.nbNewItems++;
          }
          // System.out.println(" " + dateString + " at " + timeString +
          if (added && title != null && !title.isEmpty()) {
            log.debug("Title: " + title + " cat. " + categoryString + ")");
          }
          // + " @ " + price + "( " + categoryString + ")");
        }
        catch (Exception e) {
          log.error("Parse exception ", e);
        }
      }
    }

    if (!added) {
      log.info("End");
      batch.nextURL = null;
      return batch;
    }

    // Elements nav = doc.select("div.pagination_links_container"); // a with
    // href
    Elements nextPages = doc.select("a[id=next]");
    if (nextPages.isEmpty()) {
      batch.nextURL = null;
    }
    for (Element link : nextPages) {
      String href = link.attr("href");
      if (href.startsWith("//"))
      { 
        href = "http:" + href;
      }
      batch.nextURL = href;
      
      break;
    }

    return batch;

  }

  static class ParseBatch
  {
    Repository repository;

    int nbNewItems;

    String nextURL;

    int nbIterations;

    public ParseBatch(Repository repository, String nextURL, int nbIterations, int nbNewItems) {
      super();
      this.repository = repository;
      this.nextURL = nextURL;
      this.nbIterations = nbIterations;
      this.nbNewItems = nbNewItems;
    }

  }
}
