package supersql.sql.templates;

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
