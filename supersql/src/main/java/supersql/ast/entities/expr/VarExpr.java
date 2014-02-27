package supersql.ast.entities.expr;

public class VarExpr extends Expr
{

	String name;

	public VarExpr(String varName)
	{
		super();
		name = varName;
	}

	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	public String getName()
	{
		return name;
	}

}
