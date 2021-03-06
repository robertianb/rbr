package supersql.sql.templates.oracle;

import supersql.ast.actions.CopyTableAction;
import supersql.ast.actions.CreateTempTableCopyAction;
import supersql.ast.actions.DeleteAllAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.ScriptAction;
import supersql.sql.templates.ActionTemplate;
import supersql.sql.templates.ActionTemplateCode;
import supersql.sql.templates.ActionTemplateHelper;
import supersql.sql.templates.ActionTemplateManager;

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

		ModifyColumnTypeAction anAction = (ModifyColumnTypeAction) action;
		String tableName = anAction.getTableName();

		// create temp table
		String tempTableName = tableName + "_TMP";
		ScriptAction createTempTableAction = new CreateTempTableCopyAction(
				tableName, tempTableName);
		ActionTemplate createTempTableActionTemplate = actionTemplateManager
				.getActionTemplate(vendor,
						ActionTemplateCode.CREATE_TEMP_TABLE_COPY);
		String createTempTable = createTempTableActionTemplate.apply(
				createTempTableAction, actionTemplateHelper);
		sb.append(createTempTable);

		// // Delete data from Temp table (in case is already existed and
		// contained data
		// DeleteAllAction deleteTmp = new DeleteAllAction(tempTableName);
		// ActionTemplate deleteTmpTemplate =
		// actionTemplateManager.getActionTemplate(vendor, deleteTmp);
		// String deleteTmpOut = deleteTmpTemplate.apply(deleteTmp,
		// actionTemplateHelper);
		// sb.append(deleteTmpOut);
		//
		// // copy data from table to temp table
		// CopyTableAction copyTableContentsAction = new
		// CopyTableAction(prevCreateTableAction.getTableName(),
		// tempTableName);
		// ActionTemplate copyTableContentsTemplate =
		// actionTemplateManager.getActionTemplate(vendor,
		// copyTableContentsAction);
		// String copyOut =
		// copyTableContentsTemplate.apply(copyTableContentsAction,
		// actionTemplateHelper);
		// sb.append(copyOut);

		// Delete FROM table
		DeleteAllAction deleteAllAction = new DeleteAllAction(tableName);
		ActionTemplate deleteAllActionTemplate = actionTemplateManager
				.getActionTemplate(vendor, deleteAllAction);
		String deleteOut = deleteAllActionTemplate.apply(deleteAllAction,
				actionTemplateHelper);
		sb.append(deleteOut);

		// Modify column type
		sb.append(basicModifyColumnTemplate.apply(action, actionTemplateHelper));

		// copy from Temp table back to inital table
		CopyTableAction copyTableContentsBackAction = new CopyTableAction(
				tempTableName, tableName);
		ActionTemplate copyTableContentsBackTemplate = actionTemplateManager
				.getActionTemplate(vendor, copyTableContentsBackAction);
		String copyBackOut = copyTableContentsBackTemplate.apply(
				copyTableContentsBackAction, actionTemplateHelper);
		sb.append(copyBackOut);

		// drop temp table
		// DropTableAction dropTableAction = new
		// DropTableAction(tempTableName);
		// ActionTemplate dropTableActionTemplate =
		// actionTemplateManager.getActionTemplate(vendor, dropTableAction);
		// sb.append(dropTableActionTemplate.apply(dropTableAction,
		// actionTemplateHelper));

		return sb.toString();
	}

}
