package supersql.ast.entities;

import beaver.Symbol;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 20/01/13
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
public class Columns extends Symbol {

    private List<Column> columns;


    public Columns() {
        this.columns = new ArrayList<Column>();
    }
    public Columns(List<Column> columns) {
        this.columns = columns;
    }

    public static Columns create(Column c)
    {
        Columns cd = new Columns();
        cd.addColumn(c);
        return cd;
    }
    public static Columns create(String name)
    {
        Columns cd = new Columns();
        cd.addColumn(new Column(name));
        return cd;
    }

    private void addColumn(Column c) {
        columns.add(c);
    }

    public void addColumns(Columns c) {
        columns.addAll(c.getColumns());
    }

    public List<Column> getColumns() {
        return columns;
    }
}
