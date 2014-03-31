package supersql.diff;

import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import supersql.ast.DatabaseModel;
import supersql.ast.actions.AddColumnAction;
import supersql.ast.actions.AddColumnsAction;
import supersql.ast.actions.ChangePrimaryKeyAction;
import supersql.ast.actions.CopyTableAction;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.CreateTempTableCopyAction;
import supersql.ast.actions.DeleteAllAction;
import supersql.ast.actions.DeleteColumnsAction;
import supersql.ast.actions.DropTableAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.RenameColumnAction;
import supersql.ast.actions.UpgradeVersionAction;
import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.DeleteColumnAction;
import supersql.ast.entities.TableDefinition;
import supersql.sql.ScriptSemanticsVisitor;

public class ApplicableVisitor
    implements ScriptSemanticsVisitor
{
  
  private static Logger log = Logger.getLogger(ApplicableVisitor.class);
  
  private DatabaseModel databaseModel;
  private boolean applicable;

  public ApplicableVisitor(DatabaseModel databaseModel) {
    super();
    this.databaseModel = databaseModel;
  }

  @Override
  public void createTable(CreateTableAction action) {
    applicable = true;
  }

  @Override
  public void upgradeVersion(UpgradeVersionAction action) {
    applicable = true;
  }

  @Override
  public void addColumns(AddColumnsAction addColumnsAction) {
    applicable = true;
  }

  @Override
  public void deleteColumns(DeleteColumnsAction action) {
    applicable = true;
    String tableName = action.getTableName();
    List<Column> columns = action.getColumns();
    checkColumnsExist(tableName, columns);
  }

  protected void checkColumnsExist(String tableName, List<Column> columns) {
    TableDefinition tableDefinition = databaseModel.getTableDefinition(tableName);
    applicable = tableDefinition != null; 
    if (applicable)
    {
      List<Column> columnsToDelete = columns;
      String namesOfCols = "";
      for (ColumnDefinition c : tableDefinition.getColumns()) {
        namesOfCols+=c.getName() + " ";
      }
      for (Column c : columnsToDelete) {
        if (namesOfCols.indexOf(c.getName()) < 0 )
        {
          applicable = false;
          log.info("Column " + c + " not in table definition :" + tableDefinition);
          return;
        }
      }
    }
  }

  @Override
  public void deleteColumn(DeleteColumnAction action) {
    // ROB to do
    applicable = true;
  }

  @Override
  public void addColumn(AddColumnAction action) {
    applicable = true;
  }

  @Override
  public void dropTable(DropTableAction dropTableAction) {
    applicable = true;
    checkTableExists(dropTableAction.getTableName());
  }

  protected void checkTableExists(String tableName) {
    applicable = databaseModel.getTableDefinition(tableName) != null;
  }

  @Override
  public void modifyColumn(ModifyColumnTypeAction action) {
    applicable = true;
    Column column = new Column(action.getPreviousColumnDefinition().getName());
    checkColumnsExist(action.getTableName(), Collections.singletonList(column));
  }

  @Override
  public void renameColumn(RenameColumnAction action) {
    applicable = true;
    Column column = new Column(action.getPreviousName());
    checkColumnsExist(action.getTableName(), Collections.singletonList(column));
  }

  @Override
  public void copyTableContents(CopyTableAction copyTableAction) {
    applicable = true;
  }

  @Override
  public void deleteTableContents(DeleteAllAction deleteAllAction) {
    applicable = true;
  }

  @Override
  public void copyInTempTable(CreateTempTableCopyAction createTempTableCopyAction)
  {
    applicable = true;
  }

  @Override
  public void changePrimaryKey(ChangePrimaryKeyAction changePrimaryKeyAction) {
    applicable = true;
    List<Column> newCols = changePrimaryKeyAction.getNextPK().getColumns();
    checkColumnsExist(changePrimaryKeyAction.getTableName(), newCols);
  }
  
  public boolean isApplicable() {
    return applicable;
  }

}
