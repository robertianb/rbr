package supersql.ast.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.PrimaryKeyConstraint;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 15/01/13 Time: 18:48 To change
 * this template use File | Settings | File Templates.
 */
public class CreateTableAction
    extends TableAction
{

  private PrimaryKeyConstraint primaryKey;

  public CreateTableAction(String tableName) {
    super(ActionCodes.CREATE_TABLE, new ArrayList<ColumnDefinition>(),
        tableName);
  }

  public CreateTableAction(String tableName,
                           List<ColumnDefinition> columnDefinitions,
                           PrimaryKeyConstraint primaryKey)
  {
    this(tableName, columnDefinitions);
    this.primaryKey = primaryKey;
  }

  /**
   * Creates a table with the given name and using the col definitions and PKey
   * of the given action
   * @param tableName
   * @param action
   */
  public CreateTableAction(String tableName, CreateTableAction action) {
    this(tableName, action.getColumns()); //new PrimaryKeyConstraint("PK_" + tableName, ColumnDefinition.toColumns(action.getColumns()))
  }

  @Override
  protected void setParameters() {
    super.parameters.put("tableName", tableName);
  }

  public CreateTableAction(String tableName, List<ColumnDefinition> columns) {
    super(ActionCodes.CREATE_TABLE, columns, tableName);
  }

  public CreateTableAction(String tableName, ColumnDefinition aColumn,
                           ColumnDefinition... cols)
  {
    this(tableName);
    columns.add(aColumn);
    columns.addAll(Arrays.asList(cols));
  }

  @Override
  public String toString() {
    return "CreateTableAction{" + "table[" + tableName + "]" + ", columns["
        + columns + "]}";
  }

  @Override
  public void visit(ScriptSemanticsVisitor visitor) {
    visitor.createTable(this);
  }

  public ColumnDefinition getColumnDefinition(int i) {
    if (i >= columns.size()) {
      return null;
    }
    else {
      return columns.get(i);
    }
  }

  public PrimaryKeyConstraint getPrimaryKey() {
    return primaryKey;
  }

  public int indexOfSame(ColumnDefinition colDef) {
    int index = -1;
    for (ColumnDefinition c : columns) {
      if (c.isTheSameAs(colDef)) {
        index = columns.indexOf(c);
        break;
      }
    }
    return index;
  }
}
