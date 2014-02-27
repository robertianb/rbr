package supersql.ast.entities;

import java.util.ArrayList;
import java.util.List;

import supersql.ast.entities.expr.Expr;
import supersql.ast.types.TypeDefinition;
import supersql.sql.templates.ExprTextVisitor;
import beaver.Symbol;

/**
 * Created with IntelliJ IDEA. User: ian Date: 15/01/13 Time: 18:49 To change
 * this template use File | Settings | File Templates.
 */
public class ColumnDefinition extends Symbol implements Cloneable
{
	private final String name;
	private final TypeDefinition type;
	private final boolean primary;
	private final Expr defaultExprValue;
	private final String defaultValue;

	private final boolean mandatory;
	private final ColumnConstraintDefinition constraint;

	public ColumnDefinition(String name, TypeDefinition type)
	{
		this(name, type, false);
	}

	public ColumnDefinition(String name, TypeDefinition type, boolean isPrimary)
	{
		this(name, type, isPrimary, false);
	}

	public ColumnDefinition(String name, TypeDefinition type,
			boolean isPrimary, boolean isMandatory)
	{
		this.type = type;
		this.name = name;
		primary = isPrimary;
		defaultValue = null;
		defaultExprValue = null;
		mandatory = isMandatory;
		constraint = ColumnConstraintDefinition.EMPTY;
	}

	public ColumnDefinition(String name, TypeDefinition type, Expr defaultValue)
	{
		this(name, type, defaultValue, false);
	}

	public ColumnDefinition(String name, TypeDefinition type,
			Expr defaultExprValue, boolean isMandatory)
	{
		super();
		this.name = name;
		this.type = type;
		mandatory = isMandatory;
		primary = false;
		this.defaultExprValue = defaultExprValue;
		ExprTextVisitor exprTextVisitor = new ExprTextVisitor();
		defaultExprValue.accept(exprTextVisitor);
		defaultValue = (exprTextVisitor.getString().isEmpty() ? null
				: exprTextVisitor.getString());
		constraint = ColumnConstraintDefinition.EMPTY;
	}

	public ColumnDefinition(String name, TypeDefinition type,
			Expr defaultExprValue, boolean isMandatory,
			ColumnConstraintDefinition constraint)
	{
		super();
		this.name = name;
		this.type = type;
		mandatory = isMandatory;
		primary = false;
		this.defaultExprValue = defaultExprValue;
		ExprTextVisitor exprTextVisitor = new ExprTextVisitor();
		this.defaultExprValue.accept(exprTextVisitor);
		defaultValue = (exprTextVisitor.getString().isEmpty() ? null
				: exprTextVisitor.getString());
		this.constraint = constraint;
	}

	@Override
	public String toString()
	{
		return "Column[" + name + " " + type + "default " + defaultValue + " "
				+ (mandatory ? "not null" : "") + "]";
	}

	public String getName()
	{
		return name;
	}

	public TypeDefinition getType()
	{
		return type;
	}

	public boolean isPrimary()
	{
		return primary;
	}

	public boolean isTheSameAs(Object obj)
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
		ColumnDefinition other = (ColumnDefinition) obj;
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
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		} else if (!name.equals(other.name))
		{
			return false;
		}
		if (primary != other.primary)
		{
			return false;
		}
		if (type == null)
		{
			if (other.type != null)
			{
				return false;
			}
		} else if (!type.isTheSameAs(other.type))
		{
			return false;
		}
		if (constraint == null)
		{
			if (other.constraint != null)
			{
				return false;
			}
		} else if (!constraint.equals(other.constraint))
		{
			return false;
		}
		return true;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public boolean isMandatory()
	{
		return mandatory;
	}

	public static List<Column> toColumns(
			List<ColumnDefinition> columnDefinitions)
	{
		List<Column> columns = new ArrayList<Column>();
		for (ColumnDefinition columnDefinition : columnDefinitions)
		{
			columns.add(new Column(columnDefinition.getName()));
		}
		return columns;
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}

	public ColumnDefinition copy()
	{
		try
		{
			return (ColumnDefinition) super.clone();
		} catch (CloneNotSupportedException e)
		{
			throw new IllegalStateException("Could not clone");
		}
	}

	public ColumnConstraintDefinition getConstraint()
	{
		return constraint;
	}
}
