package supersql.ast.entities;

import java.util.List;

import beaver.Symbol;

/**
 * Created with IntelliJ IDEA. User: ian Date: 15/01/13 Time: 19:08 To change
 * this template use File | Settings | File Templates.
 */
public class TableDefinition extends Symbol
{
	final private String name;
	final List<ColumnDefinition> columns;

	public TableDefinition(String name, ColumnDefinitions columns)
	{
		this.name = name;
		this.columns = columns.getColumnDefinitions();
	}

	public TableDefinition(String name, List<ColumnDefinition> columns)
	{
		this.name = name;
		this.columns = columns;
	}

	public String getName()
	{
		return name;
	}

	public List<ColumnDefinition> getColumns()
	{
		return columns;
	}

	public void removeColumn(String columnName)
	{
		for (ColumnDefinition c : columns)
		{
			if (columnName.equals(c.getName()))
			{
				columns.remove(c);
				break;
			}
		}
	}

}
