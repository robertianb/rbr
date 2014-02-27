package supersql.ast.entities.predicate;

import supersql.ast.entities.expr.Expr;

public class BetweenPredicate extends Predicate
{

	private String ident;
	private Expr bottom, top;

	public BetweenPredicate(String ident, Expr bottom, Expr top)
	{
		super();
		this.ident = ident;
		this.bottom = bottom;
		this.top = top;
	}

	@Override
	public void accept(PredicateVisitor v)
	{
		v.visit(this);
	}

	public Expr getBottom()
	{
		return bottom;
	}

	public Expr getTop()
	{
		return top;
	}

	public String getIdent()
	{
		return ident;
	}
}
