package supersql.ast.actions;

import supersql.sql.ScriptSemanticsVisitor;


public class CopyTableAction extends ScriptAction {

    
    


    private final String sourceTableName;
    private final String targetTableName;

    public CopyTableAction(String sourceTableName, String targetTableName)
    {
      super(ActionCodes.COPY_TABLE_CONTENT);
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
      visitor.copyTableContents(this);
    }
    public String getSourceTableName() {
      return sourceTableName;
    }
    
    public String getTargetTableName() {
      return targetTableName;
    }
    
}
