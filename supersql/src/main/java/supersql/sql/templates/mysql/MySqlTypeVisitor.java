package supersql.sql.templates.mysql;

import supersql.ast.Type;
import supersql.ast.types.DateTypeDefinition;
import supersql.ast.types.NVarcharTypeDefinition;
import supersql.ast.types.NumberTypeDefinition;
import supersql.ast.types.TimestampTypeDefinition;
import supersql.ast.types.TypeDefinition;
import supersql.ast.types.TypeVisitor;
import supersql.ast.types.VarcharTypeDefinition;

public class MySqlTypeVisitor
    implements TypeVisitor
{

  private String result;

  private MySqlTypeProvider typeProvider;

  public MySqlTypeVisitor() {
    this.typeProvider = new MySqlTypeProvider();
  }

  @Override
  public void varchar(VarcharTypeDefinition typeDefinition) {
    result = typeProvider.getType(Type.VARCHAR) + "("
        + typeDefinition.getNbChar() + ")";
  }

  @Override
  public void number(NumberTypeDefinition typeDefinition) {
    int nbDigit = typeDefinition.getNbDigit();
    if (nbDigit == 1) {
      result = "TINYINT";
    }
    else if (nbDigit <= 3) {
      result = "TINYINT";
    }
    else if (nbDigit <= 5) {
      result = "SMALLINT";
    }
    else if (nbDigit <= 7) {
      result = "MEDIUMINT";
    }
    else if (nbDigit <= 10) {
      result = "INT";
    }
    else if (nbDigit <= 19) {
      result = "BIGINT";
    }
    else {
      throw new IllegalStateException("Non-standard type NUMBER(" + nbDigit
          + ")");
    }
    result = result + "(" + nbDigit + ")";
  }

  @Override
  public void timestamp(TimestampTypeDefinition typeDefinition) {
    result = "DATETIME(" + typeDefinition.getPrecision() + ")";
  }

  @Override
  public void simpleType(TypeDefinition typeDefinition) {
    result = typeProvider.getType(typeDefinition.getType());
  }

  @Override
  public String getResult() {
    return result;
  }

  @Override
  public void date(DateTypeDefinition dateTypeDefinition) {
    result = "DATE";
  }

  @Override
  public void nvarchar(NVarcharTypeDefinition nVarcharTypeDefinition) {
    result = "VARCHAR(" + nVarcharTypeDefinition.getNbChar() + ") CHARACTER SET utf8";
  }

}
