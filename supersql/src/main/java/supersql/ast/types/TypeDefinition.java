package supersql.ast.types;

import supersql.ast.Type;
import beaver.Symbol;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 15/01/13
 * Time: 22:23
 * To change this template use File | Settings | File Templates.
 */
public class TypeDefinition extends Symbol{

    private Type type;

    public TypeDefinition(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "" +
                type;
    }


    public boolean isTheSameAs(Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      TypeDefinition other = (TypeDefinition) obj;
      if (type != other.type)
        return false;
      return true;
    }
    
    
    public void accept(TypeVisitor v)
    {
      v.simpleType(this);
    }
    
    
}
