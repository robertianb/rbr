package supersql.ast.entities.predicate;

import java.util.ArrayList;
import java.util.List;

import supersql.ast.entities.Column;
import supersql.ast.entities.Columns;
import supersql.ast.entities.SymbolList;

public class InListPredicate extends Predicate
{

	private SymbolList<?> values;
	private String ident;
	private List<String> names;

	public InListPredicate(String ident, SymbolList<?> values)
	{
		super();
		names = new ArrayList<String>();
		this.ident = ident;
		this.values = values;
		for (Object sth : values.getValues())
		{
			names.add(String.valueOf(sth));
		}

	}

	public InListPredicate(String i, Columns l)
	{
		super();
		names = new ArrayList<String>();
		ident = ident;
		for (Column c : l.getColumns())
		{
			names.add(c.getName());
		}
	}

	@Override
	public void accept(PredicateVisitor v)
	{
		v.visit(this);
	}

	public List<String> getNames()
	{
		return names;
	}

	public String getIdent()
	{
		return ident;
	}

}
