package supersql;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import supersql.ast.DatabaseModel;
import supersql.ast.DatabaseModelVisitor;
import supersql.ast.ScriptSemantics;
import beaver.Parser;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 29/01/13
 * Time: 22:09
 * To change this template use File | Settings | File Templates.
 */
public class CrebasReader {

    private ScriptSemantics sema;

    public CrebasReader(StringReader reader) throws FileNotFoundException, Parser.Exception {
        SupersqlScanner input = new SupersqlScanner(reader);
        parse(input);
    }
    public CrebasReader(String fileName) throws FileNotFoundException, Parser.Exception {
        SupersqlScanner input = new SupersqlScanner(new FileReader(fileName));// new
        parse(input);
    }

    private void parse(SupersqlScanner input) throws Parser.Exception {
        SupersqlParser parser = new SupersqlParser();

        try {
            sema = (ScriptSemantics) parser.parse(input);

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public DatabaseModel getDatabaseModel()
    {
        DatabaseModelVisitor visitor = new DatabaseModelVisitor();
        sema.accept(visitor);
        return visitor.getDatabaseModel();
    }
    
    public ScriptSemantics getScriptSemantics() {
      return sema;
    }
}
