package supersql.ast.actions;

import supersql.sql.ScriptSemanticsVisitor;
import supersql.sql.templates.ActionTemplateCode;


public class CreateTempTableCopyAction
    extends ScriptAction
{


  private final String sourceTableName;
  private final String targetTableName;

  public CreateTempTableCopyAction(String sourceTableName, String targetTableName)
  {
    super(ActionTemplateCode.CREATE_TEMP_TABLE_COPY);
    this.sourceTableName = sourceTableName;
    this.targetTableName = targetTableName;
    
    
    parameters.put("sourceTableName", sourceTableName);
    parameters.put("targetTableName", targetTableName);
    
  }


  @Override
  protected void setParameters() {

  }


  @Override
  public String toString() {
    return "CopyTableAction from [" + sourceTableName + "] to ["
        + targetTableName + "]";
  }


  @Override
  public void accept(ScriptSemanticsVisitor visitor) {
    visitor.copyInTempTable(this);
  }
  public String getSourceTableName() {
    return sourceTableName;
  }
  
  public String getTargetTableName() {
    return targetTableName;
  }
}
