package supersql.ast.entities;

import supersql.ast.Type;
import supersql.ast.actions.ActionCodes;
import supersql.ast.actions.ScriptAction;
import supersql.ast.types.TypeDefinition;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 20/01/13 Time: 18:49 To change
 * this template use File | Settings | File Templates.
 */
public class DeleteColumnAction extends ScriptAction
{
	private String tableName;
	private String columnName;
  private final ColumnDefinition columnDefinition;

	public DeleteColumnAction(String tableName, String columnName)
	{
		this(tableName, columnName, new ColumnDefinition(columnName, new TypeDefinition(Type.BOOLEAN)));
	}

  public DeleteColumnAction(String tableName, String colName,
                            ColumnDefinition prevCol)
  {
    super(ActionCodes.DELETE_COLUMN);
    this.columnDefinition = prevCol;
    this.tableName = tableName;
    this.columnName = colName;
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
	
	
	public ColumnDefinition getColumnDefinition() {
      return columnDefinition;
    }

}
