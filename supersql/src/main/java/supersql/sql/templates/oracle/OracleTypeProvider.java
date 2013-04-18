package supersql.sql.templates.oracle;

import supersql.ast.Type;
import supersql.sql.templates.BasicTypeProvider;

public class OracleTypeProvider
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
