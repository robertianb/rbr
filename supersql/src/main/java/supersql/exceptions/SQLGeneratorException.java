package supersql.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class SQLGeneratorException extends Exception {
    public SQLGeneratorException(String s) {
        super(s);
    }

    public SQLGeneratorException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SQLGeneratorException(Throwable throwable) {
        super(throwable);
    }
}
