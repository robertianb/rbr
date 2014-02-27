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
}
