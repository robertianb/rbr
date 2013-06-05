package supersql.sql.templates.oracle;

import supersql.ast.types.DateTypeDefinition;
import supersql.ast.types.NVarcharTypeDefinition;
import supersql.ast.types.NumberTypeDefinition;
import supersql.ast.types.TimestampTypeDefinition;
import supersql.ast.types.TypeDefinition;
import supersql.ast.types.TypeVisitor;
import supersql.ast.types.VarcharTypeDefinition;

public class OracleTypeVisitor
    implements TypeVisitor
{
  private String result;
  private OracleTypeProvider typeProvider;
  
  public OracleTypeVisitor() {
    this.typeProvider = new OracleTypeProvider();
  }
  
  @Override
  public void varchar(VarcharTypeDefinition typeDefinition) {
    result = "VARCHAR2(" + typeDefinition.getNbChar() + ")";
  }

  @Override
  public void number(NumberTypeDefinition typeDefinition) {
    result = "NUMBER(" + typeDefinition.getNbDigit() + ")";
  }

  @Override
  public void timestamp(TimestampTypeDefinition typeDefinition) {
    result = "TIMESTAMP(" + typeDefinition.getPrecision() + ")";
  }
  
  @Override
  public void date(DateTypeDefinition dateTypeDefinition) {
    result = "DATE";
  }

  @Override
  public void simpleType(TypeDefinition typeDefinition) {
    result = typeProvider.getType(typeDefinition.getType());
  }

  
  @Override
  public String getResult() {
    return result;
  }

  @Override
  public void nvarchar(NVarcharTypeDefinition nVarcharTypeDefinition) {
    result = "NVARCHAR2("  + nVarcharTypeDefinition.getNbChar() + ")";
  }
}
