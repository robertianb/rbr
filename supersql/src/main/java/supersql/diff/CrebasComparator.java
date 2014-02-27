package supersql.diff;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.Map;

import org.apache.log4j.Logger;

import supersql.CrebasReader;
import supersql.LogChangesVisitor;
import supersql.ast.DatabaseModel;
import supersql.ast.actions.CreateTableAction;
import supersql.ast.actions.DropTableAction;
import supersql.ast.actions.UpgradeVersionAction;
import supersql.ast.entities.TableDefinition;
import supersql.ast.types.TypeVisitor;
import supersql.sql.ScriptSemanticsVisitor;
import supersql.sql.templates.ActionTemplateHelper;
import supersql.sql.templates.TemplateScriptVisitor;
import supersql.sql.templates.TypeVisitorFactory;
import beaver.Parser;

/**
 * Created with IntelliJ IDEA. User: ian Date: 29/01/13 Time: 22:00 To change
 * this template use File | Settings | File Templates.
 */
public class CrebasComparator implements ScriptSemantics
{
    public static final Logger log = Logger.getLogger(CrebasComparator.class);

  private String previous;

  private String next;

  private CrebasReader previousCrebas;

  private CrebasReader nextCrebas;

  private final String previousVersion;

  private final String nextVersion;

  public CrebasComparator(String previous, String next) throws Parser.Exception {
    this(previous, next, null, null);
  }

  public CrebasComparator(String previous, String next,
                          String previousVersion, String nextVersion) throws Parser.Exception {
    this.previous = previous;
    this.next = next;
    this.previousVersion = previousVersion;
    this.nextVersion = nextVersion;

    try {
      previousCrebas = new CrebasReader(previous);
      nextCrebas = new CrebasReader(next);

    }
    catch (FileNotFoundException e) {
        throw new IllegalStateException(e);
    }
  }

    public CrebasComparator(StringReader previous, StringReader next,
                            String previousVersion, String nextVersion) throws Parser.Exception {
        this.previous = previousVersion;
        this.next = nextVersion;
        this.previousVersion = previousVersion;
        this.nextVersion = nextVersion;

        try {
            previousCrebas = new CrebasReader(previous);
            nextCrebas = new CrebasReader(next);

        }
        catch (FileNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }
  public static void main(String[] args) throws Parser.Exception {

    if (args.length < 2) {
      System.out.println("2 files expected as argument");
      return;
    }
    

    String previous = args[0];
    String next = args[1];
    
    String previousVersion = null;
    String nextVersion = null;
    if (args.length >= 4)
    {
      previousVersion = args[2];
      nextVersion = args[3];
    }
    
    String dbVendor = "oracle";
    if (args.length == 5)
    {
      dbVendor = args[4];
    }
    
    ScriptSemantics crebasComparator;
    if (previousVersion != null)
    {
      crebasComparator = new CrebasComparator(previous, next, previousVersion, nextVersion);
    } else {
      crebasComparator = new CrebasComparator(previous, next);
    }

    crebasComparator.accept(new LogChangesVisitor());
    
    TypeVisitorFactory typeVisitorFactory = new TypeVisitorFactory();
    TypeVisitor typeVisitor = typeVisitorFactory.createTypeVisitor(dbVendor);
    
    TemplateScriptVisitor scriptVisitor = new TemplateScriptVisitor(
                                                                    dbVendor, new ActionTemplateHelper(typeVisitor) {
                                                            });
    
    crebasComparator.accept(scriptVisitor);
    log.debug(scriptVisitor.getOutput());
    

  }

  /* (non-Javadoc)
 * @see supersql.diff.ScriptSemantics#accept(supersql.sql.ScriptSemanticsVisitor)
 */
@Override
public void accept(ScriptSemanticsVisitor visitor) {
    DatabaseModel previousModel = previousCrebas.getDatabaseModel();
    DatabaseModel nextModel = nextCrebas.getDatabaseModel();
    
    if (previousVersion != null )
    {
      visitor.upgradeVersion(new UpgradeVersionAction(previousVersion, nextVersion));
    }
    
    
    StringBuffer sb = new StringBuffer();
    if (previousModel.getTablesDefinitions().size() != nextModel
        .getTablesDefinitions().size()) {
      sb.append("Table models have different number of tables:\n" + previous
          + ":" + previousModel.getTablesDefinitions().size() + "\n" + next
          + ":" + nextModel.getTablesDefinitions().size() + "\n");
    }

    Map<String, TableDefinition> prevTd = previousModel.getTablesDefinitions();
    Map<String, TableDefinition> nextTd = nextModel.getTablesDefinitions();
    for (String tableName : prevTd.keySet()) {
      if (!nextTd.containsKey(tableName)) {
        sb.append("Deleted table :" + tableName + "\n");
        CreateTableAction previousCreateTableAction = previousCrebas.getScriptSemantics().getCreateTableAction(tableName);
        visitor.dropTable(new DropTableAction(tableName, previousCreateTableAction.getColumns())); 
      }
    }
    for (String tableName : nextTd.keySet()) {
      if (!prevTd.containsKey(tableName)) {
        sb.append("New table :" + tableName + "\n");
        visitor.createTable(nextCrebas.getScriptSemantics().getCreateTableAction(tableName));
      }
    }

    for (String tableName : prevTd.keySet()) {
      if (nextTd.containsKey(tableName)) {
        TableDefinition prevTableDef = prevTd.get(tableName);
        TableDefinition nextTableDef = nextTd.get(tableName);

        int newSize = nextTableDef.getColumns().size();
        int oldSize = prevTableDef.getColumns().size();
        if (oldSize != newSize) {
          sb.append("Table altered :" + tableName + " previously had "
              + oldSize + " columns and now has " + newSize + " columns.\n");
        }
        
        CreateTableActionComparator createTableActionComparator = new CreateTableActionComparator(previousCrebas.getScriptSemantics().getCreateTableAction(tableName), nextCrebas.getScriptSemantics().getCreateTableAction(tableName));
        createTableActionComparator.visit(visitor);
      }

    }

        log.debug(sb.toString());
  }

}
