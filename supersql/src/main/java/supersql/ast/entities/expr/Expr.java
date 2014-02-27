package supersql.ast.entities.expr;

public abstract class Expr extends beaver.Symbol
{
	public abstract void accept(ExprVisitor exprVisitor);
}