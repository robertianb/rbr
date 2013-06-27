package supersql.ast.actions;

import supersql.sql.ScriptSemanticsVisitor;


public class DeleteAllAction extends ScriptAction {

    

    private final String tableName;

    public DeleteAllAction(String tableName)
    {
      super(ActionCodes.DELETE_ALL);
      this.tableName = tableName;
      
      
      parameters.put("tableName", tableName);
    }


    @Override
    protected void setParameters() {

    }


    @Override
    public String toString() {
      return "DeleteAction from [" + tableName + "]";
    }


    @Override
    public void visit(ScriptSemanticsVisitor visitor) {
      visitor.deleteTableContents(this);
    }

    public String getTableName() {
      return tableName;
    }
}
