package supersql.sql.templates.mysql;

import supersql.ast.types.TypeVisitor;
import supersql.sql.templates.DefaultColumnDefinitionVisitor;

public class MySqlColumnDefinitionVisitor extends DefaultColumnDefinitionVisitor
{
  public MySqlColumnDefinitionVisitor(TypeVisitor typeVisitor)
  {
    super(typeVisitor, false);
  }

  @Override
  public String getQuotes(boolean inner) {
    return "'";
  }

}
