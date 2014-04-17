package supersql.sql.templates.h2;

import supersql.ast.types.TypeVisitor;
import supersql.sql.templates.DefaultColumnDefinitionVisitor;

public class H2ColumnDefinitionVisitor extends DefaultColumnDefinitionVisitor
{
  public H2ColumnDefinitionVisitor(TypeVisitor typeVisitor)
  {
    super(typeVisitor, false);
  }
  
  @Override
  public String getQuotes(boolean inner) {
    return "'";
  }

}
