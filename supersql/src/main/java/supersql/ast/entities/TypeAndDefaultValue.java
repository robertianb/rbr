package supersql.ast.entities;

import supersql.ast.entities.expr.EmptyExpr;
import supersql.ast.entities.expr.Expr;
import supersql.ast.types.TypeDefinition;
import beaver.Symbol;

public class TypeAndDefaultValue extends Symbol
{

	private static final Expr NO_DEFAULT_VALUE = new EmptyExpr();
	private TypeDefinition typeDefinition;
	private Expr defaultValue;

	public TypeAndDefaultValue(TypeDefinition typeDefinition)
	{
		this(typeDefinition, NO_DEFAULT_VALUE);
	}

	public TypeAndDefaultValue(TypeDefinition typeDefinition, Expr defaultValue)
	{
		super();
		this.typeDefinition = typeDefinition;
		this.defaultValue = defaultValue;
	}

	public boolean hasDefaultValue()
	{
		return defaultValue != NO_DEFAULT_VALUE;
	}

	@Override
	public String toString()
	{
		return "TypeAndDefaultValue [typeDefinition=" + typeDefinition
				+ ", defaultValue=" + defaultValue + "]";
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((defaultValue == null) ? 0 : defaultValue.hashCode());
		result = prime * result
				+ ((typeDefinition == null) ? 0 : typeDefinition.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		TypeAndDefaultValue other = (TypeAndDefaultValue) obj;
		if (defaultValue == null)
		{
			if (other.defaultValue != null)
			{
				return false;
			}
		} else if (!defaultValue.equals(other.defaultValue))
		{
			return false;
		}
		if (typeDefinition == null)
		{
			if (other.typeDefinition != null)
			{
				return false;
			}
		} else if (!typeDefinition.equals(other.typeDefinition))
		{
			return false;
		}
		return true;
	}

	public Expr getDefaultValue()
	{
		return defaultValue;
	}

	public TypeDefinition getTypeDefinition()
	{
		return typeDefinition;
	}
}
