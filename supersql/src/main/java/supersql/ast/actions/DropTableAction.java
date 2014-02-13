package supersql.ast.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

public class DropTableAction
    extends TableAction
{

  public DropTableAction(String tableName) {
    super(ActionCodes.DROP_TABLE, new ArrayList<ColumnDefinition>(), tableName);
  }

  public DropTableAction(String tableName, List<ColumnDefinition> columns) {
    super(ActionCodes.DROP_TABLE, columns, tableName);
  }

  public DropTableAction(String tableName, ColumnDefinition aColumn,
                         ColumnDefinition... cols)
  {
    this(tableName);
    columns.add(aColumn);
    columns.addAll(Arrays.asList(cols));
  }

  @Override
  protected void setParameters() {
    super.parameters.put("tableName", tableName);
  }

  @Override
  public void accept(ScriptSemanticsVisitor visitor) {
    visitor.dropTable(this);

  }

}
