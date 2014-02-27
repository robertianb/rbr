package supersql.ast.entities.expr;

public class EmptyExpr extends Expr
{
	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "";
	}
}
