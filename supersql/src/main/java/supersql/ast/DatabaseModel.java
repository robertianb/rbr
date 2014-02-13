package supersql.ast;

import java.util.HashMap;
import java.util.Map;

import supersql.ast.entities.TableDefinition;

/**
 * Created with IntelliJ IDEA. User: ian Date: 29/01/13 Time: 21:23 To change
 * this template use File | Settings | File Templates.
 */
public class DatabaseModel
{

	private HashMap<String, TableDefinition> tablesDefinitions;
	private String version;

	public DatabaseModel()
	{
		tablesDefinitions = new HashMap<String, TableDefinition>();
	}

	public Map<String, TableDefinition> getTablesDefinitions()
	{
		return tablesDefinitions;
	}

	public TableDefinition getTableDefinition(String tableName)
	{
		return tablesDefinitions.get(tableName);
	}

	public void addTable(TableDefinition tableDefinition)
	{
		tablesDefinitions.put(tableDefinition.getName(), tableDefinition);
	}

	public void removeTable(String name)
	{
		tablesDefinitions.remove(name);
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getVersion()
	{
		return version;
	}
}
