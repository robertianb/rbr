package lbc.beans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Repository
{

  private static final Logger log = Logger.getLogger(Repository.class);

  Map<String, OfferItem> items;

  public Repository() {
    items = new HashMap<>();
  }

  public Map<String, OfferItem> getItems() {
    return items;
  }

  public void load(String fileToLoad) {
    FileReader fileReader = null;
    try {
      fileReader = new FileReader(fileToLoad);
      BufferedReader bufferedReader = new BufferedReader(fileReader);

      JSONObject parsed = (JSONObject) new JSONParser().parse(fileReader);
      for (Object key : parsed.keySet()) {
        String keyS = (String) key;
        Object object = parsed.get(keyS);
        JSONObject item = (JSONObject) object;
        items.put(keyS, OfferItem.fromJSon(item));
      }

    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    finally {
      try {
        fileReader.close();
      }
      catch (IOException e) {
        // ROB Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  public void persist(String fileName) {
    BufferedWriter bw = null;
    try {
      FileWriter fileWriter = new FileWriter(fileName);
      bw = new BufferedWriter(fileWriter);
      JSONObject object = new JSONObject();
      for (String key : items.keySet()) {
        object.put(key, OfferItem.toJSon(items.get(key)));
      }

      bw.write(object.toJSONString());

      bw.flush();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    finally {
      if (bw != null) {
        try {
          bw.close();
        }
        catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public boolean addItem(OfferItem item) {
    OfferItem replaced = items.put(item.getId(), item);
    return replaced == null;
  }

  public void loadRecent() {

    Pattern pattern = Pattern.compile("items.([0-9]{8}).json");
    File dir = new File(".");
    File[] files = dir.listFiles();
    File latestFile = null;
    String maxDate = null;
    for (File file : files) {
      Matcher matcher = pattern.matcher(file.getName());
      if (matcher.find()) {
        String date = matcher.group(1);
        if (maxDate == null || maxDate.compareTo(date) < 0) {
          maxDate = date;
          latestFile = file;
        }
      }
    }
    if (latestFile != null) {
      log.info("Loading " + latestFile.getName());
      load(latestFile.getAbsolutePath());
    }

  }
  public void load() {
    Pattern pattern = Pattern.compile("items.([0-9]{8}).json");
    File dir = new File(".");
    File[] files = dir.listFiles();
    for (File file : files) {
      Matcher matcher = pattern.matcher(file.getName());
      if (matcher.find()) {        
        log.info("Loading " + file.getName());
        load(file.getAbsolutePath());
      }
    }
  }

}
