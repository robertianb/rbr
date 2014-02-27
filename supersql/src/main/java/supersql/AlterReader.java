package supersql;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

import supersql.ast.ScriptSemantics;
import beaver.Parser;

/**
 * Created with IntelliJ IDEA. User: ian Date: 29/01/13 Time: 22:09 To change
 * this template use File | Settings | File Templates.
 */
public class AlterReader
{

	private ScriptSemantics sema;

	public AlterReader(StringReader reader) throws FileNotFoundException,
			Parser.Exception
	{
		SupersqlScanner input = new SupersqlScanner(reader);
		parse(input);
	}

	public AlterReader(String fileName) throws FileNotFoundException,
			Parser.Exception
	{
		SupersqlScanner input = new SupersqlScanner(new FileReader(fileName));// new
		parse(input);
	}

	private void parse(SupersqlScanner input) throws Parser.Exception
	{
		SupersqlParser parser = new SupersqlParser();

		try
		{
			sema = (ScriptSemantics) parser.parse(input);

		} catch (IOException e)
		{
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
	}

	public ScriptSemantics getScriptSemantics()
	{
		return sema;
	}
}
