package supersql.sql.templates;

import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.ColumnDefinitionVisitor;
import supersql.ast.types.TypeVisitor;

public class DefaultColumnDefinitionVisitor
    implements ColumnDefinitionVisitor
{
  TypeVisitor typeVisitor;
  private StringBuilder result;
  private boolean escapeQuotes;


  public DefaultColumnDefinitionVisitor(TypeVisitor typeVisitor,
                                        boolean escapeQuotes)
  {
    super();
    this.typeVisitor = typeVisitor;
    this.escapeQuotes = escapeQuotes;
  }
  
  public void setEscapeQuotes(boolean escapeQuotes) {
    this.escapeQuotes = escapeQuotes;
  }

  @Override
  public void columnDefinition(ColumnDefinition colDef) {
    result = new StringBuilder();
    
    ExprTextVisitor exprTextVisitor = new ExprTextVisitor();
    String constraintString;

    if (colDef.getConstraint() != null && !colDef.getConstraint().isEmpty())
    {
        PredicateTextVisitor ptv = new PredicateTextVisitor();
        ptv.setEscapeQuotes(escapeQuotes);
        colDef.getConstraint().getPredicate().accept(ptv);
        constraintString = "CONSTRAINT " + colDef.getConstraint().getName() + " CHECK ("
                + ptv.getString() + ")";
    } else
    {
        constraintString = "";
    }
    
    result.append(colDef.getName());
    result.append( " ");
    result.append(typeVisitor.getResult());
    if (colDef.getDefaultValue() != null)
    {
      result.append(" default ");
      result.append(escapeQuotes(colDef.getDefaultValue()));
    }
    result.append(" ");
    result.append((colDef.isMandatory() ? "not null" : "null"));
//      + " " ROB constraints deactivated for the moment
//      + constraintString;
  }

  protected String escapeQuotes(String defaultValue) {
    String singleQuote = getQuotes(false);
    String escapedQuotes = getQuotes(true);
    if (defaultValue.indexOf(singleQuote) >= 0 && escapeQuotes)
    {
      defaultValue = defaultValue.replaceAll(singleQuote, escapedQuotes);
    }
    return defaultValue;
  }

  @Override
  public String getResult() {
    return result.toString();
  }

  public String getQuotes(boolean inner)
  {
    return (inner?"''":"'");
  }
}
