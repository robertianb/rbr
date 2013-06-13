package supersql.sql.templates;

import java.util.Properties;

import supersql.ast.actions.ScriptAction;

public class CreateTableIfNotExistsActionTemplate extends ActionTemplate

{
  public CreateTableIfNotExistsActionTemplate(String templateTxt) {
    super(templateTxt);
  }


    @Override
    public String apply(ScriptAction action, ActionTemplateHelper actionTemplateHelper) {
        CreateTableActionTemplate createTableActionTemplate = new CreateTableActionTemplate();
        createTableActionTemplate.setInner(true);
        createTableActionTemplate.setLineSeparator("'\n" + actionTemplateHelper.getLineFeed() + 
        		"'");

        String createBody = createTableActionTemplate.apply(action, actionTemplateHelper);

        Properties createTableParams = action.getParameters();
        createTableParams.put("createTableBody",createBody);
        String result = super.applyWithParams(actionTemplateHelper, createTableParams);

        return result;

    }
}
