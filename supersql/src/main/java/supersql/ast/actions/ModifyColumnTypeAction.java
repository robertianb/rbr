package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

// ROB 
public class ModifyColumnTypeAction extends ScriptAction
{

	private ColumnDefinition previousColumnDefinition;
	private String tableName;
	private CreateTableAction previousCreateTableAction;
	private CreateTableAction nextCreateTableAction;
	private ColumnDefinition nextColumnDefinition;

	public ModifyColumnTypeAction(String tableName,
			ColumnDefinition previousColumnDefinition,
			ColumnDefinition newColumnDefinition,
			CreateTableAction previousCreateTableAction,
			CreateTableAction nextCreateTableAction)
	{
		super(ActionCodes.MODIFY_COLUMN);
		this.tableName = tableName;
		this.previousColumnDefinition = previousColumnDefinition;
		nextColumnDefinition = newColumnDefinition;
		this.previousCreateTableAction = previousCreateTableAction;
		this.nextCreateTableAction = nextCreateTableAction;
		parameters
				.put("columnPreviousType", previousColumnDefinition.getType());
		parameters.put("columnName", newColumnDefinition.getName());
		parameters.put("tableName", tableName);
		// TODO type must be converted
		parameters.put("columnType", newColumnDefinition.getType());
		if (newColumnDefinition.getDefaultValue() != null)
        {
            parameters.put("defaultValue", newColumnDefinition.getDefaultValue());
        }
        if (newColumnDefinition.isMandatory())
        {
            parameters.put("mandatory", "not null");
        } else
        {
            parameters.put("mandatory", "null");
        }
	}

	public ModifyColumnTypeAction(String tableName,
			ColumnDefinition newColumnDefinition)
	{
		super(ActionCodes.MODIFY_COLUMN);
		this.tableName = tableName;
		nextColumnDefinition = newColumnDefinition;
		parameters.put("columnName", newColumnDefinition.getName());
		parameters.put("tableName", tableName);
		// TODO type must be converted
		parameters.put("columnType", newColumnDefinition.getType());
		if (newColumnDefinition.getDefaultValue() != null)
        {
            parameters.put("defaultValue", newColumnDefinition.getDefaultValue());
        }
        if (newColumnDefinition.isMandatory())
        {
            parameters.put("mandatory", "not null");
        } else
        {
            parameters.put("mandatory", "null");
        }
	}

	public String getTableName()
	{
		return tableName;
	}

	public ColumnDefinition getPreviousColumnDefinition()
	{
		return previousColumnDefinition;
	}

	public ColumnDefinition getNextColumnDefinition()
	{
		return nextColumnDefinition;
	}

	@Override
	protected void setParameters()
	{
	}

	public CreateTableAction getNextCreateTableAction()
	{
		return nextCreateTableAction;
	}

	public CreateTableAction getPreviousCreateTableAction()
	{
		return previousCreateTableAction;
	}

	@Override
	public void accept(ScriptSemanticsVisitor visitor)
	{
		visitor.modifyColumn(this);
	}

  public Object getDefaultValue() {
    return nextColumnDefinition.getDefaultValue();
  }

}
