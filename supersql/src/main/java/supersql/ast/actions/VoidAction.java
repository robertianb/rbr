package supersql.ast.actions;

import supersql.sql.ScriptSemanticsVisitor;

public class VoidAction
    extends ScriptAction
{

  public VoidAction() {
    super(ActionCodes.VOID_ACTION);
  }

  @Override
  protected void setParameters() {
  }

  @Override
  public void accept(ScriptSemanticsVisitor visitor) {
  }

}
