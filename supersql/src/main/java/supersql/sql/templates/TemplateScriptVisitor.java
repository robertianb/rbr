package supersql.sql.templates;

import supersql.ast.actions.ActionCodes;
import supersql.ast.actions.AddColumnAction;
import supersql.ast.actions.AddColumnsAction;
import supersql.ast.actions.CopyTableAction;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.DeleteAllAction;
import supersql.ast.actions.DeleteColumnsAction;
import supersql.ast.actions.DropTableAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.RenameColumnAction;
import supersql.ast.actions.UpgradeVersionAction;
import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.DeleteColumnAction;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class TemplateScriptVisitor implements ScriptSemanticsVisitor {



    private StringBuffer out;
    private String prefix;
    private ActionTemplateHelper actionTemplateHelper;
    private final ActionTemplateManager actionTemplateManager;
    private boolean check;


    public TemplateScriptVisitor(String prefix, ActionTemplateHelper actionTemplateHelper, boolean check) {
      this.check = check;
      this.prefix = prefix;
      this.actionTemplateHelper = actionTemplateHelper;
      this.actionTemplateManager = new ActionTemplateManager(check);
      out = new StringBuffer();
    }
    public TemplateScriptVisitor(String prefix, ActionTemplateHelper actionTemplateHelper) {
      this(prefix, actionTemplateHelper, true);
    }


    @Override
    public void createTable(CreateTableAction action) {
        ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
        out.append(actionTemplate.apply(action, actionTemplateHelper));
    }

    @Override
    public void upgradeVersion(UpgradeVersionAction action) {
        ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
        out.append(actionTemplate.apply(action, actionTemplateHelper));
    }

    @Override
    public void addColumns(AddColumnsAction action) {
        for (ColumnDefinition c : action.getColumns())
        {
            addColumn(new AddColumnAction(action.getTableName(), c));
        }
    }

    @Override
    public void deleteColumns(DeleteColumnsAction action) {
        for (Column c : action.getColumns())
        {
            deleteColumn(new DeleteColumnAction(action.getTableName(), c.getName()));
        }
    }

    @Override
    public void deleteColumn(DeleteColumnAction action) {
        ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
        out.append(actionTemplate.apply(action, actionTemplateHelper));
    }

    @Override
    public void addColumn(AddColumnAction action) {
        if (action.getDefaultValue() != null)
        {
            ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
            out.append(actionTemplate.apply(action, actionTemplateHelper));
        } else {
            ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, ActionCodes.ADD_COLUMN_WITHOUT_DEFAULT_VALUE);
            out.append(actionTemplate.apply(action, actionTemplateHelper));
        }

    }

    public StringBuffer getOutput() {
        return out;
    }


    @Override
    public void dropTable(DropTableAction action) {
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
    
    @Override
    public void modifyColumn(ModifyColumnTypeAction action) {
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }


    @Override
    public void renameColumn(RenameColumnAction action) {
      // TODO Auto-generated method stub
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }


    public void setActionTemplate(String code, ActionTemplate template)
    {
        actionTemplateManager.setActionTemplate(code, template);
    }

    public void setActionTemplateFactory(String code, ActionTemplateFactory templateFactory)
    {
        actionTemplateManager.setActionTemplateFactory(code, templateFactory);
    }
    
    public ActionTemplateManager getActionTemplateManager() {
      return actionTemplateManager;
    }
    
    @Override
    public void copyTableContents(CopyTableAction action) {
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix,action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
    
    @Override
    public void deleteTableContents(DeleteAllAction action) {
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix,action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
}
