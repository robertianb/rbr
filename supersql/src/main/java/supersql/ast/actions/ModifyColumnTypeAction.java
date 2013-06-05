package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

// ROB 
public class ModifyColumnTypeAction
    extends ScriptAction
{

  private ColumnDefinition previousColumnDefinition;
  
  public ModifyColumnTypeAction(String tableName,
                                ColumnDefinition previousColumnDefinition, ColumnDefinition newColumnDefinition)
  {
    super(ActionCodes.MODIFY_COLUMN);
    this.previousColumnDefinition = previousColumnDefinition;
    parameters.put("columnPreviousType", previousColumnDefinition.getType());
    parameters.put("columnName", newColumnDefinition.getName());
    parameters.put("tableName", tableName);
    // TODO type must be converted
    parameters.put("columnType", newColumnDefinition.getType());
  }
  
  public ColumnDefinition getPreviousColumnDefinition() {
    return previousColumnDefinition;
  }
  
  @Override
  protected void setParameters() {
  }

  @Override
  public void visit(ScriptSemanticsVisitor visitor) {
    visitor.modifyColumn(this);    
  }
  

}
