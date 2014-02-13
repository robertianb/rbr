package supersql.ast;

import java.util.ArrayList;
import java.util.List;

import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.ScriptAction;
import supersql.ast.actions.ScriptActions;
import supersql.ast.changes.TableAlter;
import supersql.sql.ScriptSemanticsVisitor;
import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 19:04
 * To change this template use File | Settings | File Templates.
 */
public class ScriptSemantics extends Symbol{
    private List<ScriptAction> actionSequence;


    private List<TableAlter> alters;
    private final DatabaseModel databaseModel;

    public ScriptSemantics() {
        this.actionSequence = new ArrayList<ScriptAction>();
        this.alters = new ArrayList<TableAlter>();
        this.databaseModel = new DatabaseModel();
    }

    public void addAction(ScriptAction a)
    {
        actionSequence.add(a);
    }

    public void addActions(ScriptActions scriptActions)
    {
        actionSequence.addAll(scriptActions.getActions());
    }

    @Override
    public String toString() {
        return "ScriptSemantics{" +
                "actionSequence=" + actionSequence +
                '}';
    }

    public void accept(ScriptSemanticsVisitor visitor)
    {
        for (ScriptAction a : actionSequence) {
            a.accept(visitor);
        }
    }
    
    public CreateTableAction getCreateTableAction(String tableName)
    {
      for (ScriptAction action : actionSequence) {
        if (action instanceof CreateTableAction)
        {
          CreateTableAction createTableAction = (CreateTableAction) action;
          if (tableName.equals( createTableAction.getTableName()))
          {
             return createTableAction;
          }
        }
      }
      return null;
    }
}
