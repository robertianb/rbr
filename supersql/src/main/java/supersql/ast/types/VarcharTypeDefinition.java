package supersql.ast.types;

import supersql.ast.Type;

public class VarcharTypeDefinition
    extends TypeDefinition
{
  private final int nbChar;

  public VarcharTypeDefinition(int nbChar) {
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
    v.varchar(this);
  }
  
}