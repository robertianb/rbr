package supersql.ast.entities.expr;

public class StringExpr extends Expr
{

	private String val;

	public StringExpr(String val)
	{
		super();
		this.val = val;
	}

	@Override
	public String toString()
	{
		return val;
	}

	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	public String getVal()
	{
		return val;
	}
}
