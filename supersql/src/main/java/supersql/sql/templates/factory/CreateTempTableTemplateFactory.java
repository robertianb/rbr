package supersql.sql.templates.factory;

import supersql.sql.templates.ActionTemplate;
import supersql.sql.templates.ActionTemplateFactory;
import supersql.sql.templates.CreateTableIfNotExistsActionTemplate;
import supersql.sql.templates.CreateTempTableActionTemplate;

/**
 * Created with IntelliJ IDEA. User: ian Date: 13/02/13 Time: 23:12 To change
 * this template use File | Settings | File Templates.
 */
public class CreateTempTableTemplateFactory
    extends ActionTemplateFactory
{

  boolean check;

  public CreateTempTableTemplateFactory(boolean check) {
    super();
    this.check = check;
  }

  @Override
  public ActionTemplate create(String templateText) {
    if (check)
    {
      return new CreateTableIfNotExistsActionTemplate(templateText, new CreateTempTableActionTemplate());
    } else {
      return new CreateTempTableActionTemplate(templateText);
    }
  }
}
