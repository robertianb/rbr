package supersql.sql.templates;

import supersql.ast.actions.ActionCodes;

public abstract class ActionTemplateFactory
{

    public abstract ActionTemplate create(String templateText);
}
