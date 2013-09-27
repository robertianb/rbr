package supersql.ast.entities;

import java.util.List;

import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 30/01/13
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
/**
 * @author rbrouard
 *
 */
public class PrimaryKeyConstraint extends Symbol {

    private String constraintId;
    private List<Column> columns;

    public PrimaryKeyConstraint(String id, List<Column> columns) {
        this.constraintId = id;
        this.columns = columns;
    }

    public PrimaryKeyConstraint(String id, Columns columnDefinitions) {
        this(id,columnDefinitions.getColumns());
    }
    


    public List<Column> getColumns() {
        return columns;
    }

    public String getConstraintId() {
        return constraintId;
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((columns == null) ? 0 : columns.hashCode());
      result = prime * result
          + ((constraintId == null) ? 0 : constraintId.hashCode());
      return result;
    }

    @Override
    public boolean equals(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      PrimaryKeyConstraint other = (PrimaryKeyConstraint) obj;
      if (columns == null) {
        if (other.columns != null)
          return false;
      }
      else if (!columns.equals(other.columns))
        return false;
      if (constraintId == null) {
        if (other.constraintId != null)
          return false;
      }
      else if (!constraintId.equals(other.constraintId))
        return false;
      return true;
    }

    @Override
    public String toString() {
      return "PK [" + constraintId
          + ", columns=" + columns + "]";
    }
    
    
    
    
    
}
