package supersql;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import supersql.diff.CrebasComparator;
import supersql.sql.templates.TemplateScriptVisitor;
import supersql.sql.templates.TypeVisitorFactory;
import supersql.sql.templates.Vendor;
import supersql.sql.templates.factory.Options;
import supersql.sql.templates.factory.TemplateScriptVisitorFactory;
import beaver.Parser.Exception;

public class CrebasScriptProducer extends ScriptProducer
{

	String vendor;
	String currentVersion, nextVersion;
	String crebas1, crebas2;

	FileWriter fileWriter1 = null;
	FileWriter fileWriter2 = null;

	public CrebasScriptProducer(Options outputOptions, String vendor,
			String prev, String next, String crebas1, String crebas2)
	{
		super(outputOptions);
		this.vendor = vendor;
		currentVersion = prev;
		nextVersion = next;
		this.crebas1 = crebas1;
		this.crebas2 = crebas2;
	}

	@Override
	protected void writeInput() throws IOException
	{

		fileWriter1 = new FileWriter("crebas1."
				+ new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date())
				+ ".sql");
		fileWriter1.append(crebas1);
		fileWriter1.flush();

		fileWriter2 = new FileWriter("crebas2."
				+ new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date())
				+ ".sql");
		fileWriter2.append(crebas2);
		fileWriter2.flush();

	}

	@Override
	protected void closeStream()
	{
		try
		{
			fileWriter1.close();
			fileWriter2.close();
		} catch (IOException e)
		{
			e.printStackTrace();
		}

	}

	@Override
	protected void tryProduce() throws Exception
	{
		TypeVisitorFactory typeVisitorFactory = new TypeVisitorFactory();
		supersql.diff.ScriptSemantics crebasComparator = new CrebasComparator(
				new StringReader(crebas1), new StringReader(crebas2),
				currentVersion, nextVersion);
		if (vendor.equalsIgnoreCase("All"))
		{

			for (String v : Vendor.ALL)
			{
				sb.append("-- ############################### " + v
						+ (Vendor.MYSQL.equals(v) ? "/MariaDB" : "")
						+ " ############################### \n");
				sb.append(produceAlterScript(v, crebasComparator,
						typeVisitorFactory, outputOptions));
			}
		} else
		{
			sb.append(produceAlterScript(vendor, crebasComparator,
					typeVisitorFactory, outputOptions));
		}

	}

	private String produceAlterScript(String vendor,
			supersql.diff.ScriptSemantics crebasComparator,
			TypeVisitorFactory typeVisitorFactory, Options options)
	{
		TemplateScriptVisitorFactory factory = new TemplateScriptVisitorFactory();
		TemplateScriptVisitor templateScriptVisitor = factory.create(vendor,
				options);
		crebasComparator.accept(templateScriptVisitor);
		return templateScriptVisitor.getOutput().toString();
	}

}
