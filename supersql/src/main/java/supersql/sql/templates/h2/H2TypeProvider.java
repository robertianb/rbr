package supersql.sql.templates.h2;

import supersql.ast.Type;
import supersql.sql.templates.BasicTypeProvider;

public class H2TypeProvider
    extends BasicTypeProvider
{
  @Override
  public String getType(Type type) {
    String toReturn;
    switch (type) {
      case VARCHAR:
        toReturn = "VARCHAR2";
        break;
      case INT:
        toReturn = "INTEGER";
      default:
        toReturn = super.getType(type);
        break;
    }
    return toReturn;
  }
}
