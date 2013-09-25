package supersql.sql.templates;

import java.util.Properties;

import supersql.ast.actions.ScriptAction;

public class CreateTableIfNotExistsActionTemplate
    extends ActionTemplate

{
  private CreateTableActionTemplate createTableActionTemplate;

  public CreateTableIfNotExistsActionTemplate(String templateTxt) {
    super(templateTxt);
    createTableActionTemplate = new CreateTableActionTemplate();
  }

  public CreateTableIfNotExistsActionTemplate(String templateText,
                                              CreateTableActionTemplate createTableActionTemplate)
  {
    super(templateText);
    this.createTableActionTemplate = createTableActionTemplate;
  }

  @Override
  public String apply(ScriptAction action,
                      ActionTemplateHelper actionTemplateHelper)
  {
    createTableActionTemplate.setInner(true);
    createTableActionTemplate.setLineSeparator(
        actionTemplateHelper.getLineFeed());

    String createBody = createTableActionTemplate.apply(action,
                                                        actionTemplateHelper);

    Properties createTableParams = action.getParameters();
    createTableParams.put("createTableBody", createBody);
    String result = super.applyWithParams(actionTemplateHelper,
                                          createTableParams);

    return result;

  }
}
