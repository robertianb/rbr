package supersql.ast.entities.expr;

public class UnaryExpr extends Expr
{
	public enum OP
	{
		UMINUS
	}

	private Expr e;
	private OP operation;

	public UnaryExpr(Expr e, OP operation)
	{
		this.e = e;
		this.operation = operation;
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

	public String getOpString()
	{
		return "-";
	}
}
