package supersql.sql.templates;

import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.ColumnDefinitionVisitor;
import supersql.ast.types.TypeVisitor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 18/01/13 Time: 00:33 To change
 * this template use File | Settings | File Templates.
 */
public abstract class ActionTemplateHelper
{

	TypeVisitor typeVisitor;
	ColumnDefinitionVisitor columnDefinitionVisitor;

	protected ActionTemplateHelper(TypeVisitor typeVisitor)
	{
		this.typeVisitor = typeVisitor;
		this.columnDefinitionVisitor = new DefaultColumnDefinitionVisitor(typeVisitor, false);
	}
	
	

	public ActionTemplateHelper(TypeVisitor typeVisitor,
                              ColumnDefinitionVisitor columnDefinitionVisitor)
  {
    super();
    this.typeVisitor = typeVisitor;
    this.columnDefinitionVisitor = columnDefinitionVisitor;
  }



  public String getColumnDefinition(ColumnDefinition colDef, boolean inner)
	{
		colDef.getType().accept(typeVisitor);
        columnDefinitionVisitor.setEscapeQuotes(inner);
		colDef.accept(columnDefinitionVisitor);
		String toReturn = columnDefinitionVisitor.getResult();
		return toReturn;
	}

	public String getColumnSeparator()
	{
		return ",";
	}

	public String getDefinitionBegin()
	{
		return "(";
	}

	public String getDefinitionEnd()
	{
		return ")";
	}

	public String getCreateTempTable()
	{
		return "create global temporary table ";
	}

	public String getCreateTable()
	{
		return "create table ";
	}

	public String getPrimaryKey(String tableName, String id,
			Column... primaryKeyColumns)
	{
		// eg. constraint PK_LEVELLEDMKTSPREADRULE primary key (ulId, bidPrice,
		// maturityValue, maturityUnit)
		StringBuffer result = new StringBuffer(" constraint " + id
				+ " primary key (");
		for (int i = 0; i < primaryKeyColumns.length - 1; i++)
		{
			Column colDef = primaryKeyColumns[i];
			result.append(colDef.getName());
			result.append(",");
		}
		if (primaryKeyColumns.length > 0)
		{
			// last
			result.append(primaryKeyColumns[primaryKeyColumns.length - 1]
					.getName());
		}
		result.append(")");
		return result.toString();
	}

	public String getSendCommand()
	{
		return "go";
	}

	public TypeVisitor getTypeVisitor()
	{
		return typeVisitor;
	}
	
	public String getQuote(boolean inner)
	{
	  return (inner?"''":"'");
	}

	public String getLineFeed()
	{
		return "'\n" + "||" + "'";
	}
}
