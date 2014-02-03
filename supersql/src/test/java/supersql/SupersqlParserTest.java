package supersql;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import supersql.ast.types.TypeVisitor;
import supersql.diff.CrebasComparator;
import supersql.sql.templates.ActionTemplateHelper;
import supersql.sql.templates.TemplateScriptVisitor;
import supersql.sql.templates.TypeVisitorFactory;
import supersql.sql.templates.Vendor;
import beaver.Parser;
import beaver.Scanner;
import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 11/01/13
 * Time: 22:34
 * To change this template use File | Settings | File Templates.
 */
public class SupersqlParserTest {

    public static final Logger log = Logger.getLogger(SupersqlParserTest.class);

    private LinkedList<String> fifo;

    @Before
    public void setUp() {
        fifo = new LinkedList<String>();
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

    @Test
    public void testDiff() throws IOException, Parser.Exception, Scanner.Exception {

        try {
            File folder = new File("src/test/resources");
            for (File f : folder.listFiles()) {
                if (f.getName().endsWith(".sql")) {
                    if (f.getName().endsWith("-next.sql")) {
                        String prefix = f.getName().substring(0, f.getName().indexOf("-next"));
                        String previousFilePath = folder.getPath() + "/" + prefix + "-previous.sql";
                        File previous = new File(previousFilePath);
                        String summaryFilePath = folder.getPath() + "/" + prefix + "-summary.sql";
                        File expectedResult = new File(summaryFilePath);
                        if (previous.exists() && expectedResult.exists()) {
                            String nextFilePath = f.getPath();
                            CrebasComparator crebasComparator = new CrebasComparator(previousFilePath, nextFilePath);
                            TypeVisitor typeVisitor = new TypeVisitorFactory().createTypeVisitor(Vendor.SUMMARY);

                            TemplateScriptVisitor scriptVisitor = new TemplateScriptVisitor(
                                    Vendor.SUMMARY, new ActionTemplateHelper(typeVisitor) {
                            });
                            crebasComparator.visit(scriptVisitor);

                            FileReader fileReader = new FileReader(expectedResult);
                            BufferedReader expectedBR = new BufferedReader(fileReader);
                            String output = scriptVisitor.getOutput().toString();
                            log.info(output);
                            BufferedReader actualBR = new BufferedReader(new StringReader(output));
                            String expectedLine = expectedBR.readLine();
                            String actualLine = actualBR.readLine();
                            while (expectedLine != null) {
                                Assert.assertEquals(expectedLine, actualLine);
                                expectedLine = expectedBR.readLine();
                                actualLine = actualBR.readLine();
                                //System.out.println(expectedLine);
                                //System.out.println(actualLine);
                            }
                            if (actualLine != null && !actualLine.isEmpty())
                            {
                                Assert.fail("Did not expect this line :" + actualLine);
                            }

                        }
                    }
                }
            }
        } catch (Exception e) {

            System.out.println(SuperSqlEvents.getInstance().getErrorSummary());
            System.out.println(fifo.toString());
            e.printStackTrace();
            Assert.fail(e.getMessage());


        }
    }

    private void parseFile(File f) throws IOException, Scanner.Exception, Parser.Exception {
        fifo.clear();
        FileReader fileReader = new FileReader(f);
        SupersqlScanner scanner = new SupersqlScanner(fileReader);

        Symbol smb = scanner.nextToken();
        do {
            String element;
            if (smb.value != null) {
                element = smb.value + "[" + smb.getId() + "]";
            } else {
                element = "" + smb.getId();
            }

            if (fifo.size() > 10) {
                fifo.removeFirst();
            }
            fifo.add(element);
            smb = scanner.nextToken();

        } while (smb != null && (smb.value == null || !smb.value.toString().equals("end-of-file")));

        scanner.yybegin(SupersqlScanner.YYINITIAL);
        SupersqlParser supersqlParser = new SupersqlParser();

        Object parse = supersqlParser.parse(scanner);
    }
}
