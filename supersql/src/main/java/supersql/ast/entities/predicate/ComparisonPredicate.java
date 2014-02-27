package supersql.ast.entities.predicate;

import supersql.ast.entities.expr.Expr;

public class ComparisonPredicate extends Predicate
{
	public enum OP
	{
		GT("GT"), LT("LT"), EQ("EQ"), GE("GE"), LE("LE");

		String name;

		private OP(String name)
		{
			this.name = name;
		}
	}

	private Expr expr1;
	private Expr expr2;
	private OP operation;

	public ComparisonPredicate(Expr expr1, Expr expr2, OP operation)
	{
		super();
		this.expr1 = expr1;
		this.expr2 = expr2;
		this.operation = operation;
	}

	@Override
	public void accept(PredicateVisitor v)
	{
		v.visit(this);
	}

	public Expr getExpr1()
	{
		return expr1;
	}

	public Expr getExpr2()
	{
		return expr2;
	}

	public OP getOperation()
	{
		return operation;
	}

}
