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

}
