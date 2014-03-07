package supersql.ast.entities.predicate;

public class AndPredicate extends Predicate
{

	private Predicate predicate1;
	private Predicate predicate2;

	public AndPredicate(Predicate predicate1, Predicate predicate2)
	{
		super();
		this.predicate1 = predicate1;
		this.predicate2 = predicate2;
	}

	public Predicate getPredicate1()
	{
		return predicate1;
	}

	public Predicate getPredicate2()
	{
		return predicate2;
	}

	@Override
	public void accept(PredicateVisitor v)
	{
		v.visit(this);
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + ((predicate1 == null) ? 0 : predicate1.hashCode());
    result = prime * result
        + ((predicate2 == null) ? 0 : predicate2.hashCode());
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
    AndPredicate other = (AndPredicate) obj;
    if (predicate1 == null) {
      if (other.predicate1 != null)
        return false;
    }
    else if (!predicate1.equals(other.predicate1))
      return false;
    if (predicate2 == null) {
      if (other.predicate2 != null)
        return false;
    }
    else if (!predicate2.equals(other.predicate2))
      return false;
    return true;
  }

	
}
