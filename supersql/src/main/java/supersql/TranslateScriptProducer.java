package supersql;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import supersql.ast.ScriptSemantics;
import supersql.sql.templates.TemplateScriptVisitor;
import supersql.sql.templates.TypeVisitorFactory;
import supersql.sql.templates.Vendor;
import supersql.sql.templates.factory.Options;
import supersql.sql.templates.factory.TemplateScriptVisitorFactory;

public class TranslateScriptProducer extends ScriptProducer
{

	String vendor;
	String currentVersion, nextVersion;
	String script;

	FileWriter fileWriter1 = null;

	public TranslateScriptProducer(Options outputOptions, String vendor,
			String prev, String next, String script)
	{
		super(outputOptions);
		this.vendor = vendor;
		currentVersion = prev;
		nextVersion = next;
		this.script = script;
	}

	@Override
	protected void writeInput() throws IOException
	{

		fileWriter1 = new FileWriter("script."
				+ new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date())
				+ ".sql");
		fileWriter1.append(script);
		fileWriter1.flush();
	}

	@Override
	protected void closeStream()
	{
		try
		{
			fileWriter1.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	protected void tryProduce() throws Exception
	{
		TypeVisitorFactory typeVisitorFactory = new TypeVisitorFactory();
		ScriptSemantics scriptSemantics = new AlterReader(new StringReader(
				script)).getScriptSemantics();
		if (vendor.equalsIgnoreCase("All"))
		{

			for (String v : Vendor.ALL)
			{
				sb.append("-- ############################### " + v
						+ (Vendor.MYSQL.equals(v) ? "/MariaDB" : "")
						+ " ############################### \n");
				sb.append(produceAlterScript(v, scriptSemantics,
						typeVisitorFactory, outputOptions));
			}
		} else
		{
			sb.append(produceAlterScript(vendor, scriptSemantics,
					typeVisitorFactory, outputOptions));
		}

	}

	private String produceAlterScript(String vendor,
			ScriptSemantics scriptSemantics,
			TypeVisitorFactory typeVisitorFactory, Options options)
	{
		TemplateScriptVisitorFactory factory = new TemplateScriptVisitorFactory();
		TemplateScriptVisitor templateScriptVisitor = factory.create(vendor,
				options);
		scriptSemantics.accept(templateScriptVisitor);
		return templateScriptVisitor.getOutput().toString();
	}

}
