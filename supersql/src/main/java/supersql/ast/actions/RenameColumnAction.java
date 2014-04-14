package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

public class RenameColumnAction
    extends ScriptAction
{
  
  private final String previousName;
  private final String tableName;
  private final ColumnDefinition newColumnDefinition;

  public RenameColumnAction(String tableName, String previousName, ColumnDefinition nextCol) {
    super(ActionCodes.RENAME_COLUMN);
    this.tableName = tableName;
    this.previousName = previousName;
    this.newColumnDefinition = nextCol;
  }

  @Override
  protected void setParameters() {
    parameters.put("tableName", tableName);
    parameters.put("previous", previousName);
    parameters.put("next", newColumnDefinition.getName());
    
    parameters.put("columnName", newColumnDefinition.getName());
    parameters.put("tableName", tableName);
    // TODO type must be converted
    parameters.put("columnType", newColumnDefinition.getType());
    parameters.put("defaultValue", (newColumnDefinition.getDefaultValue() != null?newColumnDefinition.getDefaultValue():""));
    if (newColumnDefinition.isMandatory())
    {
        parameters.put("mandatory", "not null");
    } else
    {
        parameters.put("mandatory", "null");
    }
  }

  @Override
  public void accept(ScriptSemanticsVisitor visitor) {
    visitor.renameColumn(this);

  }

  public String getTableName() {
    return tableName;
  }

  public String getPreviousName() {
    return previousName;
  }
  
  public ColumnDefinition getNewColumnDefinition() {
    return newColumnDefinition;
  }

  public Object getDefaultValue() {
    return newColumnDefinition.getDefaultValue();
  }


}
