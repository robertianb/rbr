package supersql.sql.templates.sqlserver;

import supersql.ast.types.DateTypeDefinition;
import supersql.ast.types.NVarcharTypeDefinition;
import supersql.ast.types.NumberTypeDefinition;
import supersql.ast.types.TimestampTypeDefinition;
import supersql.ast.types.TypeDefinition;
import supersql.ast.types.TypeVisitor;
import supersql.ast.types.VarcharTypeDefinition;
import supersql.sql.templates.BasicTypeProvider;

public class SqlserverTypeVisitor
    implements TypeVisitor
{
  private String result;
  private BasicTypeProvider typeProvider;
  
  public SqlserverTypeVisitor() {
    this.typeProvider = new BasicTypeProvider();
  }
  
  @Override
  public void varchar(VarcharTypeDefinition typeDefinition) {
    result = "VARCHAR(" + typeDefinition.getNbChar() + ")";
  }

  @Override
  public void number(NumberTypeDefinition typeDefinition) {
    if (typeDefinition.getNbDigit() == 1)
    {
      result = "INT";
    } else {
      result = "NUMERIC(" + typeDefinition.getNbDigit() + ",0)";
    }
  }

  @Override
  public void timestamp(TimestampTypeDefinition typeDefinition) {
    result = "DATETIME";
  }
  
  @Override
  public void date(DateTypeDefinition dateTypeDefinition) {
    result = "DATETIME";
  }

  @Override
  public void simpleType(TypeDefinition typeDefinition) {
    result = typeProvider.getType(typeDefinition.getType());
  }

  @Override
  public void nvarchar(NVarcharTypeDefinition nVarcharTypeDefinition) {
    result = "NVARCHAR("  + nVarcharTypeDefinition.getNbChar() + ")";
  }
  
  @Override
  public String getResult() {
    return result;
  }

}
