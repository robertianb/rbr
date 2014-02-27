package supersql.ast.entities.expr;

public class BinaryExpr extends Expr
{

	public enum OP
	{
		MULT, PLUS, MINUS, DIV
	}

	private Expr e1, e2;
	private OP operation;

	public BinaryExpr(Expr e1, Expr e2, OP operation)
	{
		this.e1 = e1;
		this.e2 = e2;
		this.operation = operation;
	}

	public BinaryExpr(Expr e1, char c, Expr e2)
	{
		this.e1 = e1;
		this.e2 = e2;
		switch (c)
		{
			case '*':
				operation = OP.MULT;
				break;
			case '/':
				operation = OP.DIV;
				break;
			case '+':
				operation = OP.PLUS;
				break;
			case '-':
				operation = OP.MINUS;
				break;
			default:
				throw new IllegalArgumentException("Operation not supported : "
						+ c);

		}
	}

	@Override
	public void accept(ExprVisitor exprVisitor)
	{
		exprVisitor.visit(this);
	}

	public Expr getE1()
	{
		return e1;
	}

	public Expr getE2()
	{
		return e2;
	}

	public OP getOperation()
	{
		return operation;
	}

	public String getOperationString()
	{
		String result;
		switch (operation)
		{
			case DIV:
				result = "/";
				break;
			case MINUS:
				result = "-";
				break;
			case MULT:
				result = "*";
				break;
			case PLUS:
				result = "+";
				break;
			default:
				result = "";
		}
		return result;
	}
}
