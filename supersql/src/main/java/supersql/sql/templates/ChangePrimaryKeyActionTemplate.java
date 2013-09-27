package supersql.sql.templates;

import java.util.List;
import java.util.Properties;

import supersql.ast.actions.ChangePrimaryKeyAction;
import supersql.ast.actions.ScriptAction;
import supersql.ast.entities.Column;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class ChangePrimaryKeyActionTemplate extends ActionTemplate{


    public static final String INDENT = "   ";
    public static final String DEFAULT_LINE_SEPARATOR = "\n";


    public ChangePrimaryKeyActionTemplate(String templateTxt) {
        super(templateTxt);
    }


    @Override
    public String apply(ScriptAction action, ActionTemplateHelper actionTemplateHelper) {

        StringBuffer buf = new StringBuffer();
        ChangePrimaryKeyAction changePKAction = (ChangePrimaryKeyAction) action;

        List<Column> cols = changePKAction.getNextPK().getColumns();
        String primaryKey = actionTemplateHelper.getPrimaryKey(changePKAction.getTableName(), changePKAction.getNextPK().getConstraintId(), cols.toArray(new Column[cols.size()]));
        Properties parameters = changePKAction.getParameters();
        parameters.put("nextPrimaryKey", primaryKey);
        return super.apply(changePKAction, actionTemplateHelper);
    }

}
