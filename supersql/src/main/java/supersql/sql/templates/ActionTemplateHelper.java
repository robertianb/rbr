package supersql.sql.templates;

import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.types.TypeVisitor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 18/01/13 Time: 00:33 To change
 * this template use File | Settings | File Templates.
 */
public abstract class ActionTemplateHelper
{

  TypeVisitor typeVisitor;

  protected ActionTemplateHelper(TypeVisitor typeVisitor) {
    this.typeVisitor = typeVisitor;
  }

  public String getColumnDefinition(ColumnDefinition colDef) {
    colDef.getType().visit(typeVisitor);

    return colDef.getName()
        + " "
        + typeVisitor.getResult()
        + " "
        + (colDef.isMandatory() ? "not null" : "null")
        + " "
        + (colDef.getDefaultValue() != null ? "default "
            + colDef.getDefaultValue() : "");
  }

  public String getColumnSeparator() {
    return ",";
  }

  public String getDefinitionBegin() {
    return "(";
  }

  public String getDefinitionEnd() {
    return ")";
  }

  public String getCreateTempTable() {
    return "create global temporary table ";
  }
  
  public String getCreateTable() {
    return "create table ";
  }

  public String getPrimaryKey(String tableName,
                              String id, Column... primaryKeyColumns)
  {
    // eg. constraint PK_LEVELLEDMKTSPREADRULE primary key (ulId, bidPrice,
    // maturityValue, maturityUnit)
    StringBuffer result = new StringBuffer(" constraint "
        + id + " primary key (");
    for (int i = 0; i < primaryKeyColumns.length - 1; i++) {
      Column colDef = primaryKeyColumns[i];
      result.append(colDef.getName());
      result.append(",");
    }
    if (primaryKeyColumns.length > 0) {
      // last
      result.append(primaryKeyColumns[primaryKeyColumns.length - 1].getName());
    }
    result.append(")");
    return result.toString();
  }

    public String getSendCommand() {
        return "go";
    }
    public TypeVisitor getTypeVisitor() {
        return typeVisitor;
    }

    public String getLineFeed() {
      return  "'\n" + "||" +  "'";
    }
}
