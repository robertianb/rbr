package supersql;

import org.apache.log4j.Logger;

import supersql.ast.actions.AddColumnAction;
import supersql.ast.actions.AddColumnsAction;
import supersql.ast.actions.CopyTableAction;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.CreateTempTableCopyAction;
import supersql.ast.actions.DeleteAllAction;
import supersql.ast.actions.DeleteColumnsAction;
import supersql.ast.actions.DropTableAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.RenameColumnAction;
import supersql.ast.actions.UpgradeVersionAction;
import supersql.ast.entities.DeleteColumnAction;
import supersql.sql.ScriptSemanticsVisitor;

public class LogChangesVisitor
    implements ScriptSemanticsVisitor
{
  private static Logger log = Logger.getLogger(LogChangesVisitor.class);
  @Override
  public void createTable(CreateTableAction action) {
    log.info(action);
  }

  @Override
  public void upgradeVersion(UpgradeVersionAction action) {
    log.info(action);
  }

  @Override
  public void addColumns(AddColumnsAction action) {
    log.info(action);
  }

  @Override
  public void deleteColumns(DeleteColumnsAction action) {
    log.info(action);
  }

  @Override
  public void deleteColumn(DeleteColumnAction action) {
    log.info(action);
  }

  @Override
  public void addColumn(AddColumnAction action) {
    log.info(action);
  }

  @Override
  public void dropTable(DropTableAction action) {
    log.info(action);
  }

  @Override
  public void modifyColumn(ModifyColumnTypeAction action) {
    log.info(action);
  }

  @Override
  public void renameColumn(RenameColumnAction action) {
    log.info(action);
  }

  @Override
  public void copyTableContents(CopyTableAction action) {
    log.info(action);
  }

  @Override
  public void deleteTableContents(DeleteAllAction action) {
    log.info(action);
  }
  
  @Override
  public void copyInTempTable(CreateTempTableCopyAction action)
  {
    log.info(action);
  }

}
