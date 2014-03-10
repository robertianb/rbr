package supersql.sql.templates;

import java.util.LinkedList;
import java.util.List;

import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.ScriptAction;
import supersql.ast.entities.Column;
import supersql.ast.entities.ColumnDefinition;
import supersql.ast.entities.PrimaryKeyConstraint;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 17/01/13
 * Time: 23:08
 * To change this template use File | Settings | File Templates.
 */
public class CreateTableActionTemplate extends ActionTemplate{


    public static final String INDENT = "   ";
    public static final String DEFAULT_LINE_SEPARATOR = "\n";
    private boolean inner;
    private String lineSeparator = DEFAULT_LINE_SEPARATOR;
    private boolean temp; 
    private String postTableClause = ""; 

    public CreateTableActionTemplate() {
        super("");
    }

    public CreateTableActionTemplate(String templateTxt) {
        super(templateTxt);
    }

    public CreateTableActionTemplate(String templateText,
                                     boolean temp,
                                     String postTableClause)
    {
      super(templateText);
      this.temp = temp;
      this.postTableClause = postTableClause;
    }

    @Override
    public String apply(ScriptAction action, ActionTemplateHelper actionTemplateHelper) {

        StringBuffer buf = new StringBuffer();
        CreateTableAction createTableAction = (CreateTableAction) action;

        // Comment
        String createTable = (temp?actionTemplateHelper.getCreateTempTable():actionTemplateHelper.getCreateTable());
        if (!inner)
        {
            buf.append("-- " +
            		createTable + createTableAction.getTableName() + '\n');
        }


        // CREATE TABLE
        buf.append(createTable);
        buf.append(createTableAction.getTableName());
        buf.append(' ');

        // (
        buf.append(actionTemplateHelper.getDefinitionBegin());
        buf.append(lineSeparator);

        List<Column> primaryCols = new LinkedList<Column>();

        // columns...
        List<ColumnDefinition> columns = createTableAction.getColumns();
        for (int i=0;i < columns.size() - 1 ;i++)
        {
            ColumnDefinition colDef = columns.get(i);
            buf.append(INDENT);
            buf.append(actionTemplateHelper.getColumnDefinition(colDef, inner));
            buf.append(actionTemplateHelper.getColumnSeparator());
            buf.append(lineSeparator);

            if (colDef.isPrimary())
            {
                primaryCols.add(new Column(colDef.getName()));
            }
        }
        
        
        // primary key
        // eg. constraint PK_LEVELLEDMKTSPREADRULE primary key (ulId, bidPrice, maturityValue, maturityUnit)
        PrimaryKeyConstraint primaryKeyConstraint = createTableAction.getPrimaryKey();
        
        
        if (columns.size() > 0)
        {
            // add last
            buf.append(INDENT);
            ColumnDefinition colDef = columns.get(columns.size() - 1);
            buf.append(actionTemplateHelper.getColumnDefinition(colDef, inner));
            if (primaryKeyConstraint != null)
            {
              buf.append(actionTemplateHelper.getColumnSeparator());
            }
            buf.append(lineSeparator);
            if (colDef.isPrimary())
            {
                primaryCols.add(new Column(colDef.getName()));
            }
        }


        if (primaryKeyConstraint!= null)
        {
            // use primary key constraint
            primaryCols.clear();
            primaryCols.addAll(primaryKeyConstraint.getColumns());
        }

        if (primaryCols.size() > 0)
        {
            String primaryKey = actionTemplateHelper.getPrimaryKey(createTableAction.getTableName(), primaryKeyConstraint.getConstraintId(),  primaryCols.toArray(new Column[primaryCols.size()]));
            buf.append(INDENT);
            buf.append(INDENT);
            buf.append(primaryKey);
        }


        // )
        if (!inner)
        {
            buf.append(DEFAULT_LINE_SEPARATOR);
        }
        buf.append(actionTemplateHelper.getDefinitionEnd());
        buf.append(("".equals(postTableClause)?"":" " + postTableClause));
        if (!inner)
        {
            buf.append(DEFAULT_LINE_SEPARATOR);
            buf.append(actionTemplateHelper.getSendCommand());
            buf.append(DEFAULT_LINE_SEPARATOR);
        }
        return buf.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setInner(boolean comments)
    {
        this.inner = comments;
    }



    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }
}
