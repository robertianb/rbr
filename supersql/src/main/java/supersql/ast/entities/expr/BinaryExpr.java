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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((e1 == null) ? 0 : e1.hashCode());
    result = prime * result + ((e2 == null) ? 0 : e2.hashCode());
    result = prime * result + ((operation == null) ? 0 : operation.hashCode());
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
    BinaryExpr other = (BinaryExpr) obj;
    if (e1 == null) {
      if (other.e1 != null)
        return false;
    }
    else if (!e1.equals(other.e1))
      return false;
    if (e2 == null) {
      if (other.e2 != null)
        return false;
    }
    else if (!e2.equals(other.e2))
      return false;
    if (operation != other.operation)
      return false;
    return true;
  }
	
	
}
