package supersql.ast.entities.expr;

public interface ExprVisitor
{
	void visit(BinaryExpr e);

	void visit(UnaryExpr e);

	void visit(StringExpr e);

	void visit(EmptyExpr e);

	void visit(IntExpr e);

	void visit(DoubleExpr e);

	void visit(VarExpr e);

	void visit(EnclosedExpr e);

}
