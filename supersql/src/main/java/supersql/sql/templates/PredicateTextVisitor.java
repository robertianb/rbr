package supersql.sql.templates;

import java.util.ArrayList;

import supersql.ast.entities.predicate.AndPredicate;
import supersql.ast.entities.predicate.BetweenPredicate;
import supersql.ast.entities.predicate.ComparisonPredicate;
import supersql.ast.entities.predicate.EnclosedPredicate;
import supersql.ast.entities.predicate.InListPredicate;
import supersql.ast.entities.predicate.IsNullPredicate;
import supersql.ast.entities.predicate.OrPredicate;
import supersql.ast.entities.predicate.PredicateVisitor;

public class PredicateTextVisitor implements PredicateVisitor
{

	private StringBuilder sb;
	private boolean escapeQuotes;

	public PredicateTextVisitor()
	{
		sb = new StringBuilder();
	}
	
	public void setEscapeQuotes(boolean escapeQuotes) {
      this.escapeQuotes = escapeQuotes;
    }

	@Override
	public void visit(AndPredicate predicate)
	{
		PredicateTextVisitor ptv1 = new PredicateTextVisitor();
		PredicateTextVisitor ptv2 = new PredicateTextVisitor();

		predicate.getPredicate1().accept(ptv1);
		predicate.getPredicate2().accept(ptv2);
		sb.append(ptv1.getString());
		sb.append(" AND ");
		sb.append(ptv2.getString());

	}

	public String getString()
	{
		return sb.toString();
	}

	@Override
	public void visit(BetweenPredicate predicate)
	{
		sb.append(predicate.getIdent());
		sb.append(" BETWEEN (");

		ExprTextVisitor bottomEV = new ExprTextVisitor();
		predicate.getBottom().accept(bottomEV);
		sb.append(bottomEV.getString());

		sb.append(" AND ");

		ExprTextVisitor topEV = new ExprTextVisitor();
		predicate.getTop().accept(topEV);
		sb.append(topEV.getString());

		sb.append(" )");
	}

	@Override
	public void visit(ComparisonPredicate predicate)
	{
		ExprTextVisitor etv1 = new ExprTextVisitor();
		ExprTextVisitor etv2 = new ExprTextVisitor();

		predicate.getExpr1().accept(etv1);
		predicate.getExpr2().accept(etv2);
		sb.append(etv1.getString());
		sb.append(predicate.getOperation());
		sb.append(etv2.getString());
	}

	@Override
	public void visit(EnclosedPredicate predicate)
	{
		sb.append("(");

		PredicateTextVisitor ptv1 = new PredicateTextVisitor();
		predicate.getPredicate().accept(ptv1);
		sb.append(ptv1.getString());

		sb.append(")");

	}

	@Override
	public void visit(InListPredicate predicate)
	{
		sb.append(predicate.getIdent());
		sb.append(" IN (");
		ArrayList<String> names = new ArrayList(predicate.getNames());
		for (int i = 0; i < names.size() - 1; i++)
		{
			sb.append((escapeQuotes?"''":"'"));
			sb.append(names.get(i));
			sb.append((escapeQuotes?"''":"'"));
			sb.append(",");
		}
		sb.append((escapeQuotes?"''":"'"));
		sb.append(names.get(names.size() - 1));
		sb.append((escapeQuotes?"''":"'"));
		sb.append(" )");
	}

	@Override
	public void visit(IsNullPredicate predicate)
	{
		sb.append(predicate.getIdent());
		sb.append(" IS NULL ");
	}

	@Override
	public void visit(OrPredicate predicate)
	{
		PredicateTextVisitor ptv1 = new PredicateTextVisitor();
		PredicateTextVisitor ptv2 = new PredicateTextVisitor();

		predicate.getPredicate1().accept(ptv1);
		predicate.getPredicate2().accept(ptv2);
		sb.append(ptv1.getString());
		sb.append(" OR ");
		sb.append(ptv2.getString());

	}

}
