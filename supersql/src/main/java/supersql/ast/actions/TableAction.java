package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 19/01/13
 * Time: 11:04
 * To change this template use File | Settings | File Templates.
 */
public abstract class TableAction extends ScriptAction {
    protected String tableName;
    protected List<ColumnDefinition> columns;

    public TableAction(String code, List<ColumnDefinition> columns, String tableName) {
        super(code);
        this.columns = columns;
        this.tableName = tableName;
    }

    public List<ColumnDefinition> getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }
}
