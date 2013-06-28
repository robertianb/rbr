package supersql.ast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.DeleteColumnAction;
import supersql.ast.entities.TableDefinition;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 29/01/13
 * Time: 21:31
 * To change this template use File | Settings | File Templates.
 */
public class DatabaseModelVisitor implements ScriptSemanticsVisitor {

    private final DatabaseModel databaseModel;

    public DatabaseModelVisitor() {
        databaseModel = new DatabaseModel();
    }

    @Override
    public void createTable(CreateTableAction action) {
        databaseModel.addTable(new TableDefinition(action.getTableName(), action.getColumns()));
    }

    @Override
    public void upgradeVersion(UpgradeVersionAction action) {
        databaseModel.setVersion(action.getNext());
    }

    @Override
    public void addColumns(AddColumnsAction addColumnsAction) {
        Map<String,TableDefinition> tablesDefinitions = databaseModel.getTablesDefinitions();
        String tableName = addColumnsAction.getTableName();
        if (tablesDefinitions.containsKey(tableName))
        {
            List<ColumnDefinition> columns = tablesDefinitions.get(tableName).getColumns();
            ArrayList<ColumnDefinition> newCols = new ArrayList<ColumnDefinition>();
            newCols.addAll(columns);
            newCols.addAll(addColumnsAction.getColumns());
            TableDefinition newTableDefinition = new TableDefinition(tableName, newCols);
            tablesDefinitions.put(tableName, newTableDefinition);
        }
    }

    @Override
    public void deleteColumns(DeleteColumnsAction deleteColumnsAction) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteColumn(DeleteColumnAction action) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addColumn(AddColumnAction action) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public DatabaseModel getDatabaseModel() {
        return databaseModel;
    }

    @Override
    public void dropTable(DropTableAction dropTableAction) {
      databaseModel.removeTable(dropTableAction.getTableName());
    }

    @Override
    public void modifyColumn(ModifyColumnTypeAction modifyColumnTypeAction) {
      throw new UnsupportedOperationException("Column type modificatipon is not supported");
    }

    @Override
    public void renameColumn(RenameColumnAction renameColumnAction) {
      // TODO constraints
      TableDefinition tableDefinition = databaseModel.getTablesDefinitions().get(renameColumnAction.getTableName());
      List<ColumnDefinition> newColumnDefinitions = new LinkedList<ColumnDefinition>();
      for (ColumnDefinition colDef : tableDefinition.getColumns()) {
        if (colDef.getName().equalsIgnoreCase(renameColumnAction.getPreviousName()))
        {
          newColumnDefinitions.add(new ColumnDefinition(renameColumnAction.getNewName(), colDef.getType()));
        } else {
          newColumnDefinitions.add(colDef);
        }
      }
      databaseModel.getTablesDefinitions().put(tableDefinition.getName(), new TableDefinition(tableDefinition.getName(), newColumnDefinitions));
    }

    @Override
    public void copyTableContents(CopyTableAction copyTableAction) {
//      TableDefinition sourceTableDefinition = databaseModel.getTablesDefinitions().get(copyTableAction.getSourceTableName());
//      TableDefinition targettableDefinition = databaseModel.getTablesDefinitions().get(copyTableAction.getTargetTableName());
//      if (targettableDefinition == null)
//      {
//        targettableDefinition = new TableDefinition(sourceTableDefinition.getName(), sourceTableDefinition.getColumns());
//        databaseModel.addTable(targettableDefinition);
//      }
      // nothing to do to model
    }

    @Override
    public void deleteTableContents(DeleteAllAction deleteAllAction) {
      // nothing to do to model
    }

    @Override
    public void copyInTempTable(CreateTempTableCopyAction createTempTableCopyAction)
    {
      // nothing to do to model
    }
}
