package supersql.ast.types;

import supersql.ast.Type;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 29/01/13
 * Time: 20:55
 * To change this template use File | Settings | File Templates.
 */
public class NumberTypeDefinition extends TypeDefinition {

    private int nbDigit;

    public NumberTypeDefinition(int nbDigit) {
        super(Type.NUMBER);
        this.nbDigit = nbDigit;
    }

    public int getNbDigit() {
        return nbDigit;
    }
    
    @Override
    public void visit(TypeVisitor v) {
      v.number(this);
    }
}
