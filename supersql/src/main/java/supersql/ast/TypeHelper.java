package supersql.ast;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class TypeHelper {

    public static Type getType(String s)
    {
        Type result;
        if (s.equalsIgnoreCase("int"))
        {
            result = Type.INT;
        } else if (s.equalsIgnoreCase("VARCHAR2"))
        {
            result = Type.VARCHAR;
        } else if (s.equalsIgnoreCase("float"))
        {
            result = Type.FLOAT;
        } else {
            result = Type.valueOf(s);
        }
        return result;
    }
}
