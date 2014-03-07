package supersql.ast.entities.predicate;

public class EnclosedPredicate extends Predicate
{

	private Predicate predicate;

	public EnclosedPredicate(Predicate predicate)
	{
		super();
		this.predicate = predicate;
	}

	@Override
	public void accept(PredicateVisitor v)
	{
		v.visit(this);
	}

	public Predicate getPredicate()
	{
		return predicate;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((predicate == null) ? 0 : predicate.hashCode());
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
    EnclosedPredicate other = (EnclosedPredicate) obj;
    if (predicate == null) {
      if (other.predicate != null)
        return false;
    }
    else if (!predicate.equals(other.predicate))
      return false;
    return true;
  }
	
	
}
