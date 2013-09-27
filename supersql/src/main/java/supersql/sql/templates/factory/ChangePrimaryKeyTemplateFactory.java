package supersql.sql.templates.factory;

import supersql.sql.templates.ActionTemplate;
import supersql.sql.templates.ActionTemplateFactory;
import supersql.sql.templates.ChangePrimaryKeyActionTemplate;

/**
 * Created with IntelliJ IDEA. User: ian Date: 13/02/13 Time: 23:12 To change
 * this template use File | Settings | File Templates.
 */
public class ChangePrimaryKeyTemplateFactory
    extends ActionTemplateFactory
{

  public ChangePrimaryKeyTemplateFactory() {
    super();
  }

  @Override
  public ActionTemplate create(String templateText) {
    return new ChangePrimaryKeyActionTemplate(templateText);
  }
}
