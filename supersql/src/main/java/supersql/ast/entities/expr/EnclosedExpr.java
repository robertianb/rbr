package supersql.ast.entities.expr;

public class EnclosedExpr extends Expr
{
	private Expr e;

	public EnclosedExpr(Expr e)
	{
		this.e = e;
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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((e == null) ? 0 : e.hashCode());
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
    EnclosedExpr other = (EnclosedExpr) obj;
    if (e == null) {
      if (other.e != null)
        return false;
    }
    else if (!e.equals(other.e))
      return false;
    return true;
  }
	
	
}
