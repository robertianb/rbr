package supersql.ast.entities;

import supersql.ast.actions.ActionCodes;
import supersql.ast.actions.ScriptAction;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 20/01/13 Time: 18:49 To change
 * this template use File | Settings | File Templates.
 */
public class DeleteColumnAction extends ScriptAction
{
	private String tableName;
	private String columnName;

	public DeleteColumnAction(String tableName, String columnName)
	{
		super(ActionCodes.DELETE_COLUMN);
		this.tableName = tableName;
		this.columnName = columnName;
		parameters.put("tableName", tableName);
		parameters.put("columnName", columnName);
	}

	@Override
	protected void setParameters()
	{

	}

	@Override
	public void accept(ScriptSemanticsVisitor visitor)
	{
		// To change body of implemented methods use File | Settings | File
		// Templates.
	}

	@Override
	public String toString()
	{
		return "DeleteColumnAction [" + tableName + "]  column[" + columnName
				+ "]";
	}

	public String getTableName()
	{
		return tableName;
	}

	public String getColumnName()
	{
		return columnName;
	}

}
