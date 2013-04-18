package supersql.ast.entities;

import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 18:49
 * To change this template use File | Settings | File Templates.
 */
public class Column extends Symbol {
    private final String name;


    public Column(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Column{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }


}
