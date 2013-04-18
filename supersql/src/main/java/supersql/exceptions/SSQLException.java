package supersql.exceptions;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 19:14
 * To change this template use File | Settings | File Templates.
 */
public class SSQLException extends Exception {
    public SSQLException(String s) {
        super(s);
    }

    public SSQLException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public SSQLException(Throwable throwable) {
        super(throwable);
    }
}
