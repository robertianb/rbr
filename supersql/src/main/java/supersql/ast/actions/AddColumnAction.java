package supersql.ast.actions;

import supersql.ast.entities.ColumnDefinition;
import supersql.ast.types.TypeDefinition;
import supersql.sql.ScriptSemanticsVisitor;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 20/01/13
 * Time: 14:28
 * To change this template use File | Settings | File Templates.
 */
public class AddColumnAction extends ScriptAction {

    private String tableName;
    private ColumnDefinition columnDefinition;
    
    


    public AddColumnAction(String tableName,
                           ColumnDefinition columnDefinition)
    {
      super(ActionCodes.ADD_COLUMN);
      this.tableName = tableName;
      this.columnDefinition = columnDefinition;
      
      
      parameters.put("columnName", columnDefinition.getName());
      parameters.put("tableName", tableName);
      // TODO type must be converted
      parameters.put("columnType", columnDefinition.getType());
      if (columnDefinition.getDefaultValue() != null)
      {
          parameters.put("defaultValue", columnDefinition.getDefaultValue());
      }
      if (columnDefinition.isMandatory())
      {
        parameters.put("mandatory", "not null");
      } else 
      {
        parameters.put("mandatory", "null");
      }
    }


    @Override
    protected void setParameters() {

    }

    @Override
    public void visit(ScriptSemanticsVisitor visitor) {
        visitor.addColumn(this);
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnDefinition.getName();
    }

    public TypeDefinition getColumnType() {
        return columnDefinition.getType();
    }

    public boolean isMandatory() {
        return columnDefinition.isMandatory();
    }

    public String getDefaultValue() {
        return columnDefinition.getDefaultValue();
    }

    @Override
    public String toString() {
      return "AddColumnAction [" + tableName + "] column["
          + columnDefinition.toString() + "]";
    }
    
    
}
