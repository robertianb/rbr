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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((e == null) ? 0 : e.hashCode());
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
    UnaryExpr other = (UnaryExpr) obj;
    if (e == null) {
      if (other.e != null)
        return false;
    }
    else if (!e.equals(other.e))
      return false;
    if (operation != other.operation)
      return false;
    return true;
  }
	
	
}
