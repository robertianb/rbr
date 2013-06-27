package supersql.sql.templates.factory;

import supersql.sql.templates.ActionTemplate;
import supersql.sql.templates.ActionTemplateFactory;
import supersql.sql.templates.UpgradeComponentVersionActionTemplate;

public class UpgradeVersionTemplateFactory extends ActionTemplateFactory
{

  private String component;
  
  
  
  public UpgradeVersionTemplateFactory(String component) {
    super();
    this.component = component;
  }



  @Override
  public ActionTemplate create(String templateText) {
    return new UpgradeComponentVersionActionTemplate(templateText, component);
  }
  

}
