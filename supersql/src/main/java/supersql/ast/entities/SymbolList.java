package supersql.ast.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import beaver.Symbol;

public class SymbolList<T> extends Symbol
{
	private List<T> values;

	public SymbolList()
	{
		values = new ArrayList<T>();
	}

	public SymbolList(T... vals)
	{
		this();
		values.addAll(Arrays.asList(vals));
	}

	public SymbolList append(T value)
	{
		values.add(value);
		return this;
	}

	public SymbolList append(SymbolList<T> list)
	{
		values.addAll(list.values);
		return this;
	}

	public SymbolList prepend(SymbolList<T> list)
	{
		ArrayList<T> l = new ArrayList<T>(list.values);
		l.addAll(values);
		values = l;
		return this;
	}

	public SymbolList prepend(T v)
	{
		values.add(0, v);
		return this;
	}

	public List<T> getValues()
	{
		return values;
	}

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
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
    SymbolList other = (SymbolList) obj;
    if (values == null) {
      if (other.values != null)
        return false;
    }
    else if (!values.equals(other.values))
      return false;
    return true;
  }
	
	

}
