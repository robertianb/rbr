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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((ident == null) ? 0 : ident.hashCode());
    result = prime * result + ((names == null) ? 0 : names.hashCode());
    result = prime * result + ((values == null) ? 0 : values.hashCode());
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
    InListPredicate other = (InListPredicate) obj;
    if (ident == null) {
      if (other.ident != null)
        return false;
    }
    else if (!ident.equals(other.ident))
      return false;
    if (names == null) {
      if (other.names != null)
        return false;
    }
    else if (!names.equals(other.names))
      return false;
    if (values == null) {
      if (other.values != null)
        return false;
    }
    else if (!values.equals(other.values))
      return false;
    return true;
  }
	
	

}
