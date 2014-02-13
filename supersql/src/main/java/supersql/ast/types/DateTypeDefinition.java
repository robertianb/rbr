package supersql.ast.types;

import supersql.ast.Type;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 29/01/13
 * Time: 20:56
 * To change this template use File | Settings | File Templates.
 */
public class DateTypeDefinition extends TypeDefinition {


    public DateTypeDefinition() {
        super(Type.DATE);
    }

    
    @Override
    public void accept(TypeVisitor v) {
      v.date(this);
    }
}
