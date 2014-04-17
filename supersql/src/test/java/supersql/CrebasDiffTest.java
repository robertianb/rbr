package supersql;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import supersql.diff.CrebasComparator;
import beaver.Parser;
import beaver.Scanner;
import beaver.Symbol;

/**
 * Created with IntelliJ IDEA. User: ian Date: 09/02/13 Time: 13:03 To change
 * this template use File | Settings | File Templates.
 */
public class CrebasDiffTest
{

  private LinkedList<String> fifo;
  
  class Pair<T> {
    T first;
    T second;
  }

  @Before
  public void setUp() {
    fifo = new LinkedList<String>();
    try {
      File folder = new File("src/test/resources");
      Map<String, Pair<File>> filesByPair = new HashMap<String,CrebasDiffTest.Pair<File>>();
      for (File f : folder.listFiles()) {
        if (f.getName().endsWith("-previous.sql")) {
          System.out.println("Parsing " + f.getName());
          String prefix = f.getAbsolutePath().substring(0,f.getAbsolutePath().length() - "-previous.sql".length());
          Pair<File> pair = filesByPair.get(prefix);
          if (pair == null)
          {
            pair = new Pair<File>();
            filesByPair.put(prefix, pair);
          }
          pair.first = f;
        } else if (f.getName().endsWith("-next.sql")){
          String prefix = f.getAbsolutePath().substring(0,f.getAbsolutePath().length() - "-next.sql".length());
          Pair<File> pair = filesByPair.get(prefix);
          if (pair == null)
          {
            pair = new Pair<File>();
            filesByPair.put(prefix, pair);
          }
          pair.second = f;
        }
      }
      for (Pair<File> pair : filesByPair.values())
      {
        CrebasComparator crebasComparator = new CrebasComparator(pair.first.getAbsolutePath(), pair.second.getAbsolutePath());
        crebasComparator.accept(new LogChangesVisitor());
      }
    }
    catch (Exception e) {

      System.out.println(SuperSqlEvents.getInstance().getErrorSummary());
      System.out.println(fifo.toString());
      e.printStackTrace();
      Assert.fail(e.getMessage());

    }
  }

  private void parseFile(File f)
      throws IOException, Scanner.Exception, Parser.Exception
  {
    fifo.clear();
    FileReader fileReader = new FileReader(f);
    SupersqlScanner scanner = new SupersqlScanner(fileReader);

    Symbol smb = scanner.nextToken();
    do {
      String element;
      if (smb.value != null) {
        element = smb.value + "[" + smb.getId() + "]";
      }
      else {
        element = "" + smb.getId();
      }

      if (fifo.size() > 10) {
        fifo.removeFirst();
      }
      fifo.add(element);
      smb = scanner.nextToken();

    }
    while (smb != null
        && (smb.value == null || !smb.value.toString().equals("end-of-file")));

    scanner.yybegin(SupersqlScanner.YYINITIAL);
    SupersqlParser supersqlParser = new SupersqlParser();

    Object parse = supersqlParser.parse(scanner);
  }

  

  @Test
  public void testParser() throws IOException, Parser.Exception, Scanner.Exception {

      try {
          File folder = new File("src/test/resources");
          for (File f : folder.listFiles()) {
              if (f.getName().endsWith(".sql")) {
                  System.out.println("Parsing " + f.getName());
                  parseFile(f);
              }
          }
      } catch (Exception e) {

          System.out.println(SuperSqlEvents.getInstance().getErrorSummary());
          System.out.println(fifo.toString());
          e.printStackTrace();
          Assert.fail(e.getMessage());


      }
  }

}
