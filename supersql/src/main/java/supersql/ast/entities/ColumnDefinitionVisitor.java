package supersql.ast.entities;


public interface ColumnDefinitionVisitor
{
//  public void name(String name);
//  public void  typeDefintion(TypeDefinition type);
//  public void primary(boolean primary);
//  public void defaultValue(Expr defaultValue);
//  public void defaulValue(String defaultValue);
//  public void mandatory(boolean mandatory);
//  public void constraint(ColumnConstraintDefinition constraint);
  public void columnDefinition(ColumnDefinition columnDefinition);
  
  public String getResult();
  
  public void setEscapeQuotes(boolean escapeQuotes); 
}
