package supersql;

import beaver.Parser;
import beaver.Scanner;
import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 23/01/13
 * Time: 21:19
 * To change this template use File | Settings | File Templates.
 */
public class SuperSqlEvents extends Parser.Events {

    public static final SuperSqlEvents instance = new SuperSqlEvents();
    private final StringBuffer errors;

    public SuperSqlEvents() {
        errors = new StringBuffer();
    }

    public static SuperSqlEvents getInstance() {
        return instance;
    }

    private String getPositionInfo(Symbol token) {
        return "Line :" + Symbol.getLine(token.getStart())
                + " col. "
                + Symbol.getColumn(token.getStart());
    }

    @Override
    public void syntaxError(Symbol token) {
        super.syntaxError(token);
        String positionInfo = getPositionInfo(token);
        errors.append(positionInfo + ": Syntax error on token :" + token.value
                + "\n");

    }



    @Override
    public void errorPhraseRemoved(Symbol error) {
        super.errorPhraseRemoved(error);
        errors.append(getPositionInfo(error) + "Token unexpected :" + error.value + "\n");
    }


    public void recoverFailed(Exception e) {
        errors.append("Recovery failed:" + e.getMessage() + "\n");
    }

    @Override
    public void misspelledTokenReplaced(Symbol token) {
        super.misspelledTokenReplaced(token);
        errors.append(getPositionInfo(token) + "Mispelled token replaced:" + token + "\n");
    }

    @Override
    public void scannerError(Scanner.Exception e) {
        super.scannerError(e);
        errors.append("Scanner error:" + e.getMessage() + "\n");
    }

    @Override
    public void unexpectedTokenRemoved(Symbol token) {
        super.unexpectedTokenRemoved(token);
        errors.append(getPositionInfo(token) + "Unexpected token removed:" + token + "\n");

    }

    @Override
    public void missingTokenInserted(Symbol token) {
        super.missingTokenInserted(token);
        errors.append(getPositionInfo(token) + "Inserted missing token:" + token + "\n");
    }


    public String getErrorSummary() {
        return errors.toString();
    }

    public void clear() {
        errors.setLength(0);
    }

    public boolean hasErrors() {
        return errors.length()!=0;
    }
}
