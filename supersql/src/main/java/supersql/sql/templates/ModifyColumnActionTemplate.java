package supersql.sql.templates;

import supersql.ast.actions.ScriptAction;

public class ModifyColumnActionTemplate extends ActionTemplate
{

	private final ActionTemplateManager actionTemplateManager;
	private final String vendor;

	public ModifyColumnActionTemplate(String templateText,
			ActionTemplateManager actionTemplateManager, String vendor)
	{
		super(templateText);
		this.actionTemplateManager = actionTemplateManager;
		this.vendor = vendor;
	}

	@Override
	public String apply(ScriptAction action,
			ActionTemplateHelper actionTemplateHelper)
	{

		ActionTemplate basicModifyColumnTemplate = actionTemplateManager
				.loadBasicTemplate(vendor, ActionTemplateCode.MODIFY_COLUMN);

		StringBuffer sb = new StringBuffer();

		sb.append(basicModifyColumnTemplate.apply(action, actionTemplateHelper));

		return sb.toString();
	}
}
