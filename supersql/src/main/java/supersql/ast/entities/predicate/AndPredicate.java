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

}
