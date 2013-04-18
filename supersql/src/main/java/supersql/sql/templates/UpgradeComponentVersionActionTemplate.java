package supersql.sql.templates;

import java.util.Properties;

public class UpgradeComponentVersionActionTemplate
    extends ActionTemplate
{
  private String component;

  public UpgradeComponentVersionActionTemplate(String templateText,
                                               String component)
  {
    super(templateText);
    this.component = component;
  }
  
  @Override
  protected String applyWithParams(ActionTemplateHelper actionTemplateHelper,
                                   Properties params)
  {
    params.setProperty("component", component);
    return super.applyWithParams(actionTemplateHelper, params);
  }
  

}
