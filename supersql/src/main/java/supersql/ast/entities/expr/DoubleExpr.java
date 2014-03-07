package supersql.ast.entities.expr;

public class DoubleExpr extends Expr
{
	double val;

	public DoubleExpr(double val)
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

	public double getVal()
	{
		return val;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    long temp;
    temp = Double.doubleToLongBits(val);
    result = prime * result + (int) (temp ^ (temp >>> 32));
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
    DoubleExpr other = (DoubleExpr) obj;
    if (Double.doubleToLongBits(val) != Double.doubleToLongBits(other.val))
      return false;
    return true;
  }
	
	
}
