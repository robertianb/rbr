package supersql.ast.types;

import supersql.ast.Type;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 29/01/13
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
public class TimestampTypeDefinition extends TypeDefinition {

    private int precision;

    public TimestampTypeDefinition(int precision) {
        super(Type.TIMESTAMP);
        this.precision = precision;
    }

    public int getPrecision() {
        return precision;
    }
    
    @Override
    public void accept(TypeVisitor v) {
      v.timestamp(this);
    }
    
    @Override
    public boolean isTheSameAs(Object obj) {
      return super.isTheSameAs(obj) && ((TimestampTypeDefinition) obj).precision == precision;
    }
}
