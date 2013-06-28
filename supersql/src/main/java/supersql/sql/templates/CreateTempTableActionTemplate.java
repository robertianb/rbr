package supersql.sql.templates;


public class CreateTempTableActionTemplate extends CreateTableActionTemplate

{

  public CreateTempTableActionTemplate(String templateTxt) {
    super(templateTxt, true,"ON COMMIT PRESERVE ROWS");
  }

  public CreateTempTableActionTemplate() {
    this("");
  }
}
