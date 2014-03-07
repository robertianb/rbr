package supersql.ast.entities.expr;

public class StringExpr extends Expr
{

	private String val;

	public StringExpr(String val)
	{
		super();
		this.val = val;
	}

	@Override
	public String toString()
	{
		return val;
	}

	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	public String getVal()
	{
		return val;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((val == null) ? 0 : val.hashCode());
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
    StringExpr other = (StringExpr) obj;
    if (val == null) {
      if (other.val != null)
        return false;
    }
    else if (!val.equals(other.val))
      return false;
    return true;
  }
	
	
}
