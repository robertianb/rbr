package supersql.ast.entities;

import beaver.Symbol;

public class MandatorySymbol
    extends Symbol
{
  
  private boolean mandatory;

  public MandatorySymbol(boolean mandatory) {
    super();
    this.mandatory = mandatory;
  }
  
  public boolean isMandatory() {
    return mandatory;
  }
  
}
