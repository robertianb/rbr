package supersql.sql.templates;

import java.util.Map;
import java.util.Properties;

import supersql.ast.actions.ScriptAction;
import supersql.ast.types.TypeDefinition;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 23:01
 * To change this template use File | Settings | File Templates.
 */
public class ActionTemplate {

    private ScriptAction action;
    private String templateText;

    protected ActionTemplate(String templateText) {
        this.templateText = templateText;

    }



    public String apply(ScriptAction action, ActionTemplateHelper actionTemplateHelper)
    {
        Properties params = action.getParameters();

        String res = applyWithParams(actionTemplateHelper, params);
        return res;
    }

    protected String applyWithParams(ActionTemplateHelper actionTemplateHelper, Properties params) {
        String res = templateText;
        for (Map.Entry entry : params.entrySet())
        {
            Object value = entry.getValue();
            String valueToString;
            if (value instanceof TypeDefinition)
            {
                ((TypeDefinition) value).accept(actionTemplateHelper.getTypeVisitor());
                valueToString = actionTemplateHelper.getTypeVisitor().getResult();
            } else {
                valueToString = value.toString();
            }
            res = res.replaceAll("\\$\\{" + (String) entry.getKey() + "\\}", valueToString);
            res = res.replaceAll("\\$\\{\\{" + (String) entry.getKey() + "\\}\\}", (valueToString.indexOf('\'')>=0?"'" + valueToString + "'":valueToString));
        }
        return res;
    }


}
