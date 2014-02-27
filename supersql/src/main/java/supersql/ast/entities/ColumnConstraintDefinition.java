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

}
