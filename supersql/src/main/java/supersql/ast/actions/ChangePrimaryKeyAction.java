package supersql.ast.actions;

import supersql.ast.entities.PrimaryKeyConstraint;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 18/01/13 Time: 19:13 To change
 * this template use File | Settings | File Templates.
 */
public class ChangePrimaryKeyAction
    extends ScriptAction
{

  private final String tableName;

  private final CreateTableAction previousCreateTableAction;

  private final CreateTableAction nextCreateTableAction;

  private final PrimaryKeyConstraint previousPK;

  private final PrimaryKeyConstraint nextPK;

  public ChangePrimaryKeyAction(String tableName,
                                PrimaryKeyConstraint previousPK,
                                PrimaryKeyConstraint nextPK,
                                CreateTableAction previousCreateTableAction,
                                CreateTableAction nextCreateTableAction)
  {
    super(ActionCodes.CHANGE_PRIMARY_KEY);
    this.tableName = tableName;
    this.previousPK = previousPK;
    this.nextPK = nextPK;
    this.previousCreateTableAction = previousCreateTableAction;
    this.nextCreateTableAction = nextCreateTableAction;
    parameters.put("previousPrimaryKey", previousPK);
    parameters.put("nextPrimaryKey", nextPK);
    parameters.put("nextConstraintId", nextPK.getConstraintId());
    parameters.put("previousConstraintId", previousPK.getConstraintId());
    parameters.put("tableName", tableName);
    // TODO type must be converted
  }

  public String getTableName() {
    return tableName;
  }

  public PrimaryKeyConstraint getNextPK() {
    return nextPK;
  }

  public PrimaryKeyConstraint getPreviousPK() {
    return previousPK;
  }

  @Override
  protected void setParameters() {
  }

  public CreateTableAction getNextCreateTableAction() {
    return nextCreateTableAction;
  }

  public CreateTableAction getPreviousCreateTableAction() {
    return previousCreateTableAction;
  }

  @Override
  public void visit(ScriptSemanticsVisitor visitor) {
    visitor.changePrimaryKey(this);
  }

}
