package supersql.diff;

import org.apache.log4j.Logger;

import supersql.ast.actions.AddColumnAction;
import supersql.ast.actions.ChangePrimaryKeyAction;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.RenameColumnAction;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.DeleteColumnAction;
import supersql.ast.entities.PrimaryKeyConstraint;
import supersql.sql.ScriptSemanticsVisitor;

public class CreateTableActionComparator {

    private static Logger log = Logger
            .getLogger(CreateTableActionComparator.class);

    private CreateTableAction previousCreateTableAction;

    private CreateTableAction nextCreateTableAction;

    private String tableName;

    public CreateTableActionComparator(CreateTableAction previousCreateTableAction,
                                       CreateTableAction nextCreateTableAction) {
        super();
        this.previousCreateTableAction = previousCreateTableAction;
        this.nextCreateTableAction = nextCreateTableAction;
        this.tableName = this.nextCreateTableAction.getTableName();
    }

    public void visit(ScriptSemanticsVisitor visitor) {
        int newIndex = 0;
        
     // Check PKey
        PrimaryKeyConstraint previousPK = previousCreateTableAction.getPrimaryKey();
        PrimaryKeyConstraint nextPK = nextCreateTableAction.getPrimaryKey();
        ChangePrimaryKeyAction changePrimaryKeyAction = null;
        if (previousPK!=null && !previousPK.equals(nextPK))
        {
          if (log.isDebugEnabled()) {
            log.debug("Primary key has changed, previous[" + previousPK +
                    "] new [" + nextPK + "]");
          }
          
          changePrimaryKeyAction = new ChangePrimaryKeyAction(tableName, previousPK, nextPK, previousCreateTableAction, nextCreateTableAction);
          visitor.changePrimaryKey(changePrimaryKeyAction);
        }
        
        for (int i = 0; i < previousCreateTableAction.getColumns().size(); i++) {
            ColumnDefinition prevCol = previousCreateTableAction
                    .getColumnDefinition(i);
            ColumnDefinition nextCol = nextCreateTableAction.getColumnDefinition(newIndex);

            if (nextCol == null) {
                // columns were deleted
                visitor.deleteColumn(new DeleteColumnAction(tableName, prevCol
                        .getName(), prevCol));
                newIndex++;
            } else if (prevCol.isTheSameAs(nextCol)) {
                // column unchanged, do nothing
              newIndex++;
            } else {
                // column is not the same
                if (prevCol.getName().equalsIgnoreCase(nextCol.getName())) {
                    // same name, the type or default value might have changed
                    // TODO manage default value & type
                    visitor.modifyColumn(new ModifyColumnTypeAction(tableName, prevCol, nextCol, previousCreateTableAction , nextCreateTableAction));
                    newIndex++;
                } else if (nextCreateTableAction.indexOfSame(prevCol) >= 0) {
                    // column still exists in next version, but has been shifted
                    log.warn(this + "Seems like column [" + nextCol.getName()
                            + "] was inserted before column [" + prevCol.getName()
                            + "] instead of at the end of the table");
                    int newIndexShifted = nextCreateTableAction.indexOfSame(prevCol);
                    for (int ci = newIndex; ci < newIndexShifted; ci++) {
                        // add intermediary cols
                        nextCol = nextCreateTableAction.getColumnDefinition(ci);
                        if (previousCreateTableAction.indexOfSame(nextCol) < 0)
                        {
                          visitor.addColumn(new AddColumnAction(tableName, nextCol));
                        }
                    }
                    newIndex = newIndexShifted;
                } else if (prevCol.getType().isTheSameAs(nextCol.getType())) {
                    // type is the same, could be a rename, or a delete
                  if (previousCreateTableAction.indexOfSame(nextCol) >= 0) {
                    // column was deleted
                    log.warn(this + "Supposing column [" + prevCol.getName()
                        + "] was deleted");
                    visitor.deleteColumn(new DeleteColumnAction(tableName, prevCol
                        .getName(), prevCol));
                  }
                  else {
                    log.warn(this + "Supposing column [" + prevCol.getName()
                        + "] was renamed to [" + nextCol.getName() + "]");
                    visitor.renameColumn(new RenameColumnAction(tableName, prevCol
                        .getName(), nextCol));
                  }
                  newIndex++;
                } else {
                    // type and name have changed, looks like a delete and and insert
                    log.warn(this + "Supposing column [" + prevCol.getName()
                            + "] was deleted and column [" + nextCol.getName()
                            + "] was added in its place");
                    visitor.deleteColumn(new DeleteColumnAction(tableName, prevCol
                            .getName(), prevCol));
                    if (previousCreateTableAction.indexOfSame(nextCol) >= 0) {
                    } else {
                      
                    }
                }
            }
        }
        

        // iterate over remaining columns if any (table has more columns than
        // before)
        while (newIndex < nextCreateTableAction
                .getColumns().size()) {
            // all these are new columns, supposing new columns are always added last
            ColumnDefinition nextCol = nextCreateTableAction.getColumnDefinition(newIndex);
            if (previousCreateTableAction.indexOfSame(nextCol) < 0)
            {
              visitor.addColumn(new AddColumnAction(tableName, nextCol));
            }
            newIndex++;
        }
        
        
        
    }

    @Override
    public String toString() {
        return "CreateTableActionComparator table[" + tableName + "]";
    }

}
