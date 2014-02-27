package supersql.ast.entities.expr;

public class DoubleExpr extends Expr
{
	double val;

	public DoubleExpr(double val)
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

	public double getVal()
	{
		return val;
	}
}
