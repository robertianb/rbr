package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;
import supersql.sql.ScriptSemanticsVisitor;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 18/01/13
 * Time: 19:13
 * To change this template use File | Settings | File Templates.
 */
public class AddColumnsAction extends TableAction {

    public AddColumnsAction(String tableName, List<ColumnDefinition> columns) {
        super(ActionCodes.ADD_COLUMNS, columns, tableName);
    }

    @Override
    protected void setParameters() {
        // no params
    }

    @Override
    public void visit(ScriptSemanticsVisitor visitor) {
        visitor.addColumns(this);
    }
}
