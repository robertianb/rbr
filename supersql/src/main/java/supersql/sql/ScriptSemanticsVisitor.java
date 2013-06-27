package supersql.sql;

import supersql.ast.actions.AddColumnAction;
import supersql.ast.actions.AddColumnsAction;
import supersql.ast.actions.CopyTableAction;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.DeleteAllAction;
import supersql.ast.actions.DeleteColumnsAction;
import supersql.ast.actions.DropTableAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.RenameColumnAction;
import supersql.ast.actions.UpgradeVersionAction;
import supersql.ast.entities.DeleteColumnAction;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 22:50
 * To change this template use File | Settings | File Templates.
 */
public interface ScriptSemanticsVisitor {

    void createTable(CreateTableAction action);

    void upgradeVersion(UpgradeVersionAction action);


    void addColumns(AddColumnsAction addColumnsAction);

    void deleteColumns(DeleteColumnsAction deleteColumnsAction);

    void deleteColumn(DeleteColumnAction action);

    void addColumn(AddColumnAction action);

    void dropTable(DropTableAction dropTableAction);

    void modifyColumn(ModifyColumnTypeAction modifyColumnTypeAction);

    void renameColumn(RenameColumnAction renameColumnAction);

    void copyTableContents(CopyTableAction copyTableAction);

    void deleteTableContents(DeleteAllAction deleteAllAction);
}
