package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

// ROB 
public class ModifyColumnTypeAction
    extends ScriptAction
{

  private ColumnDefinition previousColumnDefinition;
  private final String tableName;
  private final CreateTableAction previousCreateTableAction;
  private final CreateTableAction nextCreateTableAction;
  private final ColumnDefinition nextColumnDefinition;
  
  public ModifyColumnTypeAction(String tableName,
                                ColumnDefinition previousColumnDefinition, ColumnDefinition newColumnDefinition, CreateTableAction previousCreateTableAction, CreateTableAction nextCreateTableAction)
  {
    super(ActionCodes.MODIFY_COLUMN);
    this.tableName = tableName;
    this.previousColumnDefinition = previousColumnDefinition;
    this.nextColumnDefinition = newColumnDefinition;
    this.previousCreateTableAction = previousCreateTableAction;
    this.nextCreateTableAction = nextCreateTableAction;
    parameters.put("columnPreviousType", previousColumnDefinition.getType());
    parameters.put("columnName", newColumnDefinition.getName());
    parameters.put("tableName", tableName);
    // TODO type must be converted
    parameters.put("columnType", newColumnDefinition.getType());
  }
  
  public String getTableName() {
    return tableName;
  }
  
  public ColumnDefinition getPreviousColumnDefinition() {
    return previousColumnDefinition;
  }
  
  public ColumnDefinition getNextColumnDefinition() {
    return nextColumnDefinition;
  }
  
  
  @Override
  protected void setParameters() {
  }
  
  public CreateTableAction getNextCreateTableAction() {
    return nextCreateTableAction;
  }
  
  public CreateTableAction getPreviousCreateTableAction() {
    return previousCreateTableAction;
  }

  @Override
  public void visit(ScriptSemanticsVisitor visitor) {
    visitor.modifyColumn(this);    
  }
  

}
