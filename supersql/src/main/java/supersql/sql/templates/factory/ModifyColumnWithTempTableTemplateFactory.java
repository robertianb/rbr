package supersql.sql.templates.factory;

import supersql.sql.templates.ActionTemplate;
import supersql.sql.templates.ActionTemplateFactory;
import supersql.sql.templates.ActionTemplateManager;
import supersql.sql.templates.ModifyColumnActionTemplate;

public class ModifyColumnWithTempTableTemplateFactory
    extends ActionTemplateFactory
{

  private final ActionTemplateManager actionTemplateManager;
  private final String vendor;

  public ModifyColumnWithTempTableTemplateFactory(String vendor, ActionTemplateManager actionTemplateManager)
  {
    this.vendor = vendor;
    this.actionTemplateManager = actionTemplateManager;
  }

  @Override
  public ActionTemplate create(String templateText) {
    return new ModifyColumnActionTemplate(templateText,actionTemplateManager, vendor);
  }

}
