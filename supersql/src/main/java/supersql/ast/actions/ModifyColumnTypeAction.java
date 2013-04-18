package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;

// ROB 
public class ModifyColumnTypeAction
    extends AddColumnAction
{

  private ColumnDefinition previousColumnDefinition;
  
  public ModifyColumnTypeAction(String tableName,
                                ColumnDefinition previousColumnDefinition, ColumnDefinition newColumnDefinition)
  {
    super(tableName, newColumnDefinition);
    this.previousColumnDefinition = previousColumnDefinition;
  }
  
  public ColumnDefinition getPreviousColumnDefinition() {
    return previousColumnDefinition;
  }
  

}
