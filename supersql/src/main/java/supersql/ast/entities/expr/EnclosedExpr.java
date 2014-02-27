package supersql.ast.entities.expr;

public class EnclosedExpr extends Expr
{
	private Expr e;

	public EnclosedExpr(Expr e)
	{
		this.e = e;
	}

	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	public Expr getE()
	{
		return e;
	}
}
