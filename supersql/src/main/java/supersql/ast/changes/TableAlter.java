package supersql.ast.changes;

import supersql.ast.entities.TableDefinition;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public class TableAlter {

    private TableDefinition preDefinition;
    private TableDefinition postDefinition;

    public TableAlter(TableDefinition preDefinition, TableDefinition postDefinition) {
        this.preDefinition = preDefinition;
        this.postDefinition = postDefinition;
    }
}
