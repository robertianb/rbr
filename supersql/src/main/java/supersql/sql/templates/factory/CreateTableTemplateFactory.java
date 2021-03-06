package supersql.sql.templates.factory;

import supersql.sql.templates.ActionTemplate;
import supersql.sql.templates.ActionTemplateFactory;
import supersql.sql.templates.CreateTableActionTemplate;
import supersql.sql.templates.CreateTableIfNotExistsActionTemplate;

/**
 * Created with IntelliJ IDEA. User: ian Date: 13/02/13 Time: 23:12 To change
 * this template use File | Settings | File Templates.
 */
public class CreateTableTemplateFactory
    extends ActionTemplateFactory
{

  boolean check;

  public CreateTableTemplateFactory(boolean check) {
    super();
    this.check = check;
  }

  @Override
  public ActionTemplate create(String templateText) {
    if (check)
    {
      return new CreateTableIfNotExistsActionTemplate(templateText);
    } else {
      return new CreateTableActionTemplate(templateText);
    }
  }
}
