package supersql.ast.entities.expr;

public class IntExpr extends Expr
{
	int val;

	public IntExpr(int val)
	{
		super();
		this.val = val;
	}

	@Override
	public String toString()
	{
		return "" + val;
	}

	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	public int getVal()
	{
		return val;
	}

}
