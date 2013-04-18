package supersql.sql.templates;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 13/02/13
 * Time: 23:12
 * To change this template use File | Settings | File Templates.
 */
public class CreateTableTemplateFactory extends ActionTemplateFactory {


    @Override
    public ActionTemplate create(String templateText) {
        return new CreateTableIfNotExistsActionTemplate(templateText);
    }
}
