package supersql.ast.actions;

import beaver.Symbol;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 23:07
 * To change this template use File | Settings | File Templates.
 */
public class ScriptActions extends Symbol{
    List<ScriptAction> actions;

    public ScriptActions() {
        this.actions = new LinkedList<ScriptAction>();
    }

    public static ScriptActions create(ScriptAction a)
    {
        ScriptActions sa = new ScriptActions();
        sa.addScriptAction(a);
        return sa;
    }

    public void addScriptAction(ScriptAction a)
    {
        actions.add(a);
    }

    public void addScriptActions(ScriptActions sa)
    {
        actions.addAll(sa.getActions());
    }


    public List<ScriptAction> getActions() {
        return actions;
    }
}
