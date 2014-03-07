package supersql.ast.entities.predicate;

public class IsNullPredicate extends Predicate
{

	private String ident;

	public IsNullPredicate(String ident)
	{
		super();
		this.ident = ident;
	}

	@Override
	public void accept(PredicateVisitor v)
	{
		v.visit(this);
	}

	public String getIdent()
	{
		return ident;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ident == null) ? 0 : ident.hashCode());
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
    IsNullPredicate other = (IsNullPredicate) obj;
    if (ident == null) {
      if (other.ident != null)
        return false;
    }
    else if (!ident.equals(other.ident))
      return false;
    return true;
  }
	
	

}
