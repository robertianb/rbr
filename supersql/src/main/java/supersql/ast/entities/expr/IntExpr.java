package supersql.ast.entities.expr;

public class IntExpr extends Expr
{
	int val;

	public IntExpr(int val)
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

	public int getVal()
	{
		return val;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + val;
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
    IntExpr other = (IntExpr) obj;
    if (val != other.val)
      return false;
    return true;
  }

	
}
