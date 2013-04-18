package supersql.ast.entities;

import beaver.Symbol;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 22:59
 * To change this template use File | Settings | File Templates.
 */
public class ColumnDefinitions extends Symbol {

    private List<ColumnDefinition> columnDefinitions;

    public ColumnDefinitions() {
        columnDefinitions = new LinkedList<ColumnDefinition>();
    }

    public void addColumnDef(ColumnDefinition c)
    {
        columnDefinitions.add(c);
    }

    public List<ColumnDefinition> getColumnDefinitions() {
        return columnDefinitions;
    }

    public static ColumnDefinitions create(ColumnDefinition c)
    {
        ColumnDefinitions cd = new ColumnDefinitions();
        cd.addColumnDef(c);
        return cd;
    }
}
