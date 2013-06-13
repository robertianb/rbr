package supersql.ast.types;

import supersql.ast.Type;

public class NVarcharTypeDefinition
    extends TypeDefinition
{
  private final int nbChar;

  public NVarcharTypeDefinition(int nbChar) {
    super(Type.VARCHAR);
    this.nbChar = nbChar;
  }
  
  public int getNbChar() {
    return nbChar;
  }

  @Override
  public String toString() {
    return "VARCHAR(" + nbChar + ")";
  }
  
  @Override
  public void visit(TypeVisitor v) {
    v.nvarchar(this);
  }
  
}
