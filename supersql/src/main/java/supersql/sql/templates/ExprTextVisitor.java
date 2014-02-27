package supersql.sql.templates;

import supersql.ast.entities.expr.BinaryExpr;
import supersql.ast.entities.expr.DoubleExpr;
import supersql.ast.entities.expr.EmptyExpr;
import supersql.ast.entities.expr.EnclosedExpr;
import supersql.ast.entities.expr.ExprVisitor;
import supersql.ast.entities.expr.IntExpr;
import supersql.ast.entities.expr.StringExpr;
import supersql.ast.entities.expr.UnaryExpr;
import supersql.ast.entities.expr.VarExpr;

public class ExprTextVisitor implements ExprVisitor
{

	private StringBuilder sb;

	public ExprTextVisitor()
	{
		sb = new StringBuilder();
	}

	@Override
	public void visit(BinaryExpr e)
	{
		ExprTextVisitor e1v = new ExprTextVisitor();
		ExprTextVisitor e2v = new ExprTextVisitor();

		e.getE1().accept(e1v);
		e.getE1().accept(e2v);
		sb.append(e1v.getString());
		sb.append(e.getOperationString());
		sb.append(e2v.getString());
	}

	public String getString()
	{
		return sb.toString();
	}

	@Override
	public void visit(UnaryExpr e)
	{
		sb.append(e.getOpString());

		ExprTextVisitor ev = new ExprTextVisitor();
		e.getE().accept(ev);
		sb.append(ev.getString());
	}

	@Override
	public void visit(StringExpr e)
	{
		// sb.append("'");
		sb.append(e.getVal());
		// sb.append("'");
	}

	@Override
	public void visit(EmptyExpr e)
	{
		//
	}

	@Override
	public void visit(IntExpr e)
	{
		sb.append(e.getVal());
	}

	@Override
	public void visit(DoubleExpr e)
	{
		sb.append(e.getVal());
	}

	@Override
	public void visit(VarExpr e)
	{
		sb.append(e.getName());
	}

	@Override
	public void visit(EnclosedExpr e)
	{
		sb.append("(");

		ExprTextVisitor ev = new ExprTextVisitor();
		e.getE().accept(ev);
		sb.append(ev.getString());

		sb.append(")");
	}

}
