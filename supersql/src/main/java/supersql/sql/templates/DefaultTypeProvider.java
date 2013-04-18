package supersql.sql.templates;

import supersql.ast.Type;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 18/01/13
 * Time: 00:39
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTypeProvider {

    public String getType(Type type)
    {
        return type.name();
    }

}
