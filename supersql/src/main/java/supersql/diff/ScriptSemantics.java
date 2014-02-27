package supersql.diff;

import supersql.sql.ScriptSemanticsVisitor;

public interface ScriptSemantics
{

	public abstract void accept(ScriptSemanticsVisitor visitor);

}