package supersql.ast.entities;

import supersql.ast.entities.predicate.Predicate;
import beaver.Symbol;

public class ColumnConstraintDefinition extends Symbol
{

	public static ColumnConstraintDefinition EMPTY = new ColumnConstraintDefinition(
			"empty", null);
	private String name;
	private Predicate predicate;

	public ColumnConstraintDefinition(String name, Predicate predicate)
	{
		super();
		this.name = name;
		this.predicate = predicate;
	}

	public boolean isEmpty()
	{
		return EMPTY == this;
	}

	public Predicate getPredicate()
	{
		return predicate;
	}

	public String getName()
	{
		return name;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
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
    ColumnConstraintDefinition other = (ColumnConstraintDefinition) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    }
    else if (!name.equals(other.name))
      return false;
    if (predicate == null) {
      if (other.predicate != null)
        return false;
    }
    else if (!predicate.equals(other.predicate))
      return false;
    return true;
  }

	
	
}
