package supersql.sql.templates.factory;

import supersql.ast.DatabaseModel;
import supersql.ast.actions.ActionCodes;
import supersql.sql.templates.ActionTemplateCode;
import supersql.sql.templates.ActionTemplateHelperFactory;
import supersql.sql.templates.TemplateScriptVisitor;
import supersql.sql.templates.Vendor;

public class TemplateScriptVisitorFactory
{

    public TemplateScriptVisitor create(String vendor, Options options)
    { 
      return create(vendor, options, new DatabaseModel());
    }
	/**
	 * @param vendor
	 * @param options
	 * @param databaseModel initial model
	 * @return
	 */
	public TemplateScriptVisitor create(String vendor, Options options, DatabaseModel databaseModel)
	{
		ActionTemplateHelperFactory helperFactory = new ActionTemplateHelperFactory();
		boolean check = options.isCheckIfExists();
		TemplateScriptVisitor scriptVisitor = new TemplateScriptVisitor(vendor,
				helperFactory.createHelper(vendor), check, databaseModel);
		if (!Vendor.SUMMARY.equals(vendor))
		{
			// create table template
			scriptVisitor.setActionTemplateFactory(ActionCodes.CREATE_TABLE,
					new CreateTableTemplateFactory(check));
			scriptVisitor.setActionTemplateFactory(
					ActionCodes.CHANGE_PRIMARY_KEY,
					new ChangePrimaryKeyTemplateFactory());
			scriptVisitor.setActionTemplateFactory(
					ActionTemplateCode.CREATE_TEMP_TABLE,
					new CreateTempTableTemplateFactory(check));
			scriptVisitor.setActionTemplateFactory(ActionCodes.MODIFY_COLUMN,
					new ModifyColumnWithTempTableTemplateFactory(vendor,
							scriptVisitor.getActionTemplateManager()));
		}
		scriptVisitor.setActionTemplateFactory(
				ActionCodes.UPGRADE_VERSION,
				new UpgradeVersionTemplateFactory(options
						.getComponentToUpdate()));
		return scriptVisitor;
	}

}
