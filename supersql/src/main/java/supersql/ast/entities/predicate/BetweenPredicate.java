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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((bottom == null) ? 0 : bottom.hashCode());
    result = prime * result + ((ident == null) ? 0 : ident.hashCode());
    result = prime * result + ((top == null) ? 0 : top.hashCode());
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
    BetweenPredicate other = (BetweenPredicate) obj;
    if (bottom == null) {
      if (other.bottom != null)
        return false;
    }
    else if (!bottom.equals(other.bottom))
      return false;
    if (ident == null) {
      if (other.ident != null)
        return false;
    }
    else if (!ident.equals(other.ident))
      return false;
    if (top == null) {
      if (other.top != null)
        return false;
    }
    else if (!top.equals(other.top))
      return false;
    return true;
  }
	
	
}
