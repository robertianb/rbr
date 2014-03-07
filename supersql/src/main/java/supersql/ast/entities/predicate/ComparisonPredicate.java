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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((expr1 == null) ? 0 : expr1.hashCode());
    result = prime * result + ((expr2 == null) ? 0 : expr2.hashCode());
    result = prime * result + ((operation == null) ? 0 : operation.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    ComparisonPredicate other = (ComparisonPredicate) obj;
    if (expr1 == null) {
      if (other.expr1 != null)
        return false;
    }
    else if (!expr1.equals(other.expr1))
      return false;
    if (expr2 == null) {
      if (other.expr2 != null)
        return false;
    }
    else if (!expr2.equals(other.expr2))
      return false;
    if (operation != other.operation)
      return false;
    return true;
  }
	
	

}
