package supersql.ast.entities;

import java.util.List;

import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 30/01/13
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class PrimaryKeyConstraint extends Symbol {

    private String constraintId;
    private List<Column> columns;

    public PrimaryKeyConstraint(String id, List<Column> columns) {
        this.constraintId = id;
        this.columns = columns;
    }

    public PrimaryKeyConstraint(String id, Columns columnDefinitions) {
        this(id,columnDefinitions.getColumns());
    }
    


    public List<Column> getColumns() {
        return columns;
    }

    public String getConstraintId() {
        return constraintId;
    }
}
