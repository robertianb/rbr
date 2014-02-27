package supersql.ast.entities.predicate;

import beaver.Symbol;

public abstract class Predicate extends Symbol
{
	public abstract void accept(PredicateVisitor v);
}
