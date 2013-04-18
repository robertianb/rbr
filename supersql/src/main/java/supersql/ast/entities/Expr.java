package supersql.ast.entities;


public class Expr extends beaver.Symbol
{
    public double val;

    public Expr(double val)
    {
        super();
        this.val = val;
    }
    

    public Expr(int value) {
      super();
      this.val = value;
    }
}