package supersql.ast.actions;

import beaver.Symbol;
import supersql.sql.ScriptSemanticsVisitor;

import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 18:47
 * To change this template use File | Settings | File Templates.
 */
public abstract class ScriptAction extends Symbol {
    private String code;
    protected Properties parameters;

    protected ScriptAction(String code) {
        this.code = code;
        this.parameters = new Properties();
    }

    protected abstract void setParameters();

    public abstract void visit(ScriptSemanticsVisitor visitor);

    public Properties getParameters()
    {
        // TODO ugly
        setParameters();
        return parameters;
    }



    public String getCode() {
        return code;
    }
}
