package supersql.ast.actions;

import supersql.sql.ScriptSemanticsVisitor;

public class RenameColumnAction
    extends ScriptAction
{
  
  private final String previousName;
  private final String newName;
  private final String tableName;

  public RenameColumnAction(String tableName, String previousName, String newName) {
    super(ActionCodes.RENAME_COLUMN);
    this.tableName = tableName;
    this.previousName = previousName;
    this.newName = newName;
  }

  @Override
  protected void setParameters() {
    parameters.put("tableName", tableName);
    parameters.put("previous", previousName);
    parameters.put("next", newName);
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

  public String getNewName() {
    return newName;
  }
  
  

}
