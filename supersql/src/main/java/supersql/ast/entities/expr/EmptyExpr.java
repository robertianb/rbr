package supersql.ast.entities.expr;

public class EmptyExpr extends Expr
{
	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	@Override
	public String toString()
	{
		return "";
	}
	
	@Override
	public boolean equals(Object obj) {
	  if (this == obj)
	      return true;
	    if (obj == null)
	      return false;
	    if (getClass() != obj.getClass())
	      return false;
	    return true;
	}
	
	@Override
	public int hashCode() {
	  return 0;
	}
	
}
