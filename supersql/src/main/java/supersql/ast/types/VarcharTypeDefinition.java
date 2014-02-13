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
  public void accept(TypeVisitor v) {
    v.varchar(this);
  }
  
  @Override
  public boolean isTheSameAs(Object obj) {
    return super.isTheSameAs(obj) && ((VarcharTypeDefinition) obj).nbChar == nbChar;
  }
}
