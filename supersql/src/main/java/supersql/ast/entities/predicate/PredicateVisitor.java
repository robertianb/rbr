package supersql.ast.entities.predicate;


public interface PredicateVisitor
{
	void visit(AndPredicate predicate);

	void visit(BetweenPredicate predicate);

	void visit(ComparisonPredicate predicate);

	void visit(EnclosedPredicate predicate);

	void visit(InListPredicate predicate);

	void visit(IsNullPredicate predicate);

	void visit(OrPredicate predicate);

}
