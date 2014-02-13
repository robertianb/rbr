package supersql.ast.actions;

import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 19/01/13
 * Time: 11:44
 * To change this template use File | Settings | File Templates.
 */
public class DeleteColumnsAction extends ScriptAction {

    private List<Column> columns;
    private String tableName;

    public DeleteColumnsAction(String tableName, List<Column> columns) {
        super(ActionCodes.DELETE_COLUMNS);
        this.columns = columns;
        this.tableName = tableName;
    }

    @Override
    protected void setParameters() {

    }

    @Override
    public void accept(ScriptSemanticsVisitor visitor) {
        visitor.deleteColumns(this);
    }

    public List<Column> getColumns() {
        return columns;
    }

    public String getTableName() {
        return tableName;
    }
}
