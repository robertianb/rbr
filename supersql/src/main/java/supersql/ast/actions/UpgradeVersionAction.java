package supersql.ast.actions;

import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 22:52
 * To change this template use File | Settings | File Templates.
 */
public class UpgradeVersionAction extends ScriptAction {


    private String previous;
    private String next;


    public UpgradeVersionAction(String previous, String next) {
        super(ActionCodes.UPGRADE_VERSION);
        this.previous = previous;
        this.next = next;
    }

    public String getPrevious() {
        return previous;
    }

    public String getNext() {
        return next;
    }


    @Override
    protected void setParameters() {
        super.parameters.put("previous", previous);
        super.parameters.put("next", next);
    }

    @Override
    public void accept(ScriptSemanticsVisitor visitor) {
        visitor.upgradeVersion(this);
    }
}
