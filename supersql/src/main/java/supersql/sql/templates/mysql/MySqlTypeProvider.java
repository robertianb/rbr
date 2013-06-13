package supersql.sql.templates.mysql;

import supersql.ast.Type;
import supersql.sql.templates.BasicTypeProvider;

public class MySqlTypeProvider
    extends BasicTypeProvider
{
  @Override
  public String getType(Type type) {
    String toReturn;
    switch (type) {
      case VARCHAR:
        toReturn = "VARCHAR";
        break;
      case INT:
        toReturn = "INT";
      default:
        toReturn = super.getType(type);
        break;
    }
    return toReturn;
  }
}
