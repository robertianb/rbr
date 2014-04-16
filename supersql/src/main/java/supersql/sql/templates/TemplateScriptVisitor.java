package supersql.sql.templates;

import org.apache.log4j.Logger;

import supersql.ast.DatabaseModel;
import supersql.ast.actions.ActionCodes;
import supersql.ast.actions.AddColumnAction;
import supersql.ast.actions.AddColumnsAction;
import supersql.ast.actions.ChangePrimaryKeyAction;
import supersql.ast.actions.CopyTableAction;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.CreateTempTableCopyAction;
import supersql.ast.actions.DeleteAllAction;
import supersql.ast.actions.DeleteColumnsAction;
import supersql.ast.actions.DropTableAction;
import supersql.ast.actions.ModifyColumnTypeAction;
import supersql.ast.actions.RenameColumnAction;
import supersql.ast.actions.UpgradeVersionAction;
import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.DeleteColumnAction;
import supersql.diff.ApplicableVisitor;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class TemplateScriptVisitor implements ScriptSemanticsVisitor {



  private static Logger log = Logger.getLogger(TemplateScriptVisitor.class);
    private StringBuffer out;
    private StringBuffer remainingActionsBuffer;
    private String prefix;
    private ActionTemplateHelper actionTemplateHelper;
    private final ActionTemplateManager actionTemplateManager;
    private boolean check;
    private DatabaseModel databaseModel;
    private ApplicableVisitor applicableVisitor;


    public TemplateScriptVisitor(String prefix, ActionTemplateHelper actionTemplateHelper, boolean check, DatabaseModel model) {
      databaseModel = model;
      this.check = check;
      this.prefix = prefix;
      this.actionTemplateHelper = actionTemplateHelper;
      this.actionTemplateManager = new ActionTemplateManager(check);
      applicableVisitor = new ApplicableVisitor(databaseModel);
      out = new StringBuffer();
      remainingActionsBuffer = new StringBuffer();
    }
    
    public TemplateScriptVisitor(String prefix, ActionTemplateHelper actionTemplateHelper, boolean check) {
      this (prefix, actionTemplateHelper, check, new DatabaseModel());
    }
    
    public TemplateScriptVisitor(String prefix, ActionTemplateHelper actionTemplateHelper) {
      this(prefix, actionTemplateHelper, true);
    }


    @Override
    public void createTable(CreateTableAction action) {
        applicableVisitor.createTable(action);
        ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
        out.append(actionTemplate.apply(action, actionTemplateHelper));
    }

    @Override
    public void upgradeVersion(UpgradeVersionAction action) {
        applicableVisitor.upgradeVersion(action);
        ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
        out.append(actionTemplate.apply(action, actionTemplateHelper));
    }

    @Override
    public void addColumns(AddColumnsAction action) {
        applicableVisitor.addColumns(action);
        for (ColumnDefinition c : action.getColumns())
        {
            addColumn(new AddColumnAction(action.getTableName(), c));
        }
    }

    @Override
    public void deleteColumns(DeleteColumnsAction action) {
        applicableVisitor.deleteColumns(action); 
        for (Column c : action.getColumns())
        {
            deleteColumn(new DeleteColumnAction(action.getTableName(), c.getName()));
        }
    }

  @Override
  public void deleteColumn(DeleteColumnAction action) {
    if (action.getColumnDefinition().getDefaultValue() != null) {
      ActionTemplate actionTemplate = actionTemplateManager
          .getActionTemplate(prefix,
                             ActionCodes.DELETE_COLUMN_WITH_DEFAULT_VALUE);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
//       out.append("-- <<<<<<<<<<<<<< WARNING >>>>>>>>>>>>>>\n");
//       out.append("-- Deleting column with default value $\n");
//       out.append("-- <<<<<<<<<<<<<< ------- >>>>>>>>>>>>>>\n");
    }
    else {
      ActionTemplate actionTemplate = actionTemplateManager
          .getActionTemplate(prefix, action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
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
    if (remainingActionsBuffer != null) {
      out.append(remainingActionsBuffer.toString());
      remainingActionsBuffer = null;
    }
    return out;
  }


    @Override
    public void dropTable(DropTableAction action) {
      applicableVisitor.dropTable(action);
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
    
    @Override
    public void modifyColumn(ModifyColumnTypeAction action) {
      applicableVisitor.modifyColumn(action);
      if (action.getDefaultValue() != null)
      {
          ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, ActionCodes.MODIFY_COLUMN_WITH_DEFAULT_VALUE);
          out.append(actionTemplate.apply(action, actionTemplateHelper));
      } else {
        ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
          out.append(actionTemplate.apply(action, actionTemplateHelper));
      }
    }


    @Override
    public void renameColumn(RenameColumnAction action) {
      applicableVisitor.renameColumn(action);
      if (action.getDefaultValue() != null)
      {
          ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, action);
          out.append(actionTemplate.apply(action, actionTemplateHelper));
      } else {
          ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix, ActionCodes.RENAME_COLUMN_WITHOUT_DEFAULT_VALUE);
          out.append(actionTemplate.apply(action, actionTemplateHelper));
      }
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
      applicableVisitor.copyTableContents(action);
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix,action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
    
    @Override
    public void deleteTableContents(DeleteAllAction action) {
      applicableVisitor.deleteTableContents(action);
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix,action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
    @Override
    public void copyInTempTable(CreateTempTableCopyAction action)
    {
      applicableVisitor.copyInTempTable(action);
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix,action);
      out.append(actionTemplate.apply(action, actionTemplateHelper));
    }
   
    @Override
    public void changePrimaryKey(ChangePrimaryKeyAction action)
    {
      applicableVisitor.changePrimaryKey(action);
      ActionTemplate actionTemplate = actionTemplateManager.getActionTemplate(prefix,action);
      if (applicableVisitor.isApplicable())
      {
        out.append(actionTemplate.apply(action, actionTemplateHelper));   
      } else {
        remainingActionsBuffer.append(actionTemplate.apply(action, actionTemplateHelper));
      }
    }
}
