package supersql.ast.types;

public interface TypeVisitor
{
  
  void varchar(VarcharTypeDefinition typeDefinition);
  
  void number(NumberTypeDefinition typeDefinition);
  
  void timestamp(TimestampTypeDefinition typeDefinition);

  void simpleType(TypeDefinition typeDefinition);

  String getResult();

  void date(DateTypeDefinition dateTypeDefinition);

  void nvarchar(NVarcharTypeDefinition nVarcharTypeDefinition);

}
