package supersql.sql.templates;

import supersql.sql.templates.h2.H2ColumnDefinitionVisitor;
import supersql.sql.templates.h2.H2TypeVisitor;
import supersql.sql.templates.mysql.MySqlColumnDefinitionVisitor;
import supersql.sql.templates.mysql.MySqlTypeVisitor;
import supersql.sql.templates.oracle.OracleTypeVisitor;
import supersql.sql.templates.sqlserver.SqlserverTypeVisitor;
import supersql.sql.templates.sybase.SybaseTypeVisitor;

// TODO handle all specific stuff 
public class ActionTemplateHelperFactory
{
  
  private static final String SEMICOLON = ";";
  private static final String GO = "go";

  public ActionTemplateHelper createHelper(String vendor)
  {
    ActionTemplateHelper result;
    
    if (Vendor.ORACLE.equals(vendor))
    {
      result = new ActionTemplateHelper(new OracleTypeVisitor()) {
        @Override
        public String getSendCommand() {
          return "/";
        }
      };
    } else if (Vendor.SQLSERVER.equals(vendor))
    {
      result = new ActionTemplateHelper(new SqlserverTypeVisitor()) {
        @Override
        public String getSendCommand() {
          return GO;
        }
        
        @Override
        public String getLineFeed() {
          return "'\n" + "+" +  "'";
        }
      };
    } else if (Vendor.SYBASE.equals(vendor))
    {
      result = new ActionTemplateHelper(new SybaseTypeVisitor()) {
        @Override
        public String getSendCommand() {
          return GO;
        }
      };
    } else if (Vendor.MYSQL.equals(vendor))
    {
      MySqlTypeVisitor mySqlTypeVisitor = new MySqlTypeVisitor();
      result = new ActionTemplateHelper(mySqlTypeVisitor, new MySqlColumnDefinitionVisitor(mySqlTypeVisitor)) {
        @Override
        public String getSendCommand() {
          return SEMICOLON;
        }
        
        @Override
        public String getQuote(boolean inner) {
          return "'";
        }
        
        @Override
        public String getLineFeed() {
         return "\n";
        }
      };
    } else if (Vendor.H2.equals(vendor))
    {
      H2TypeVisitor h2TypeVisitor = new H2TypeVisitor();
      result = new ActionTemplateHelper(h2TypeVisitor, new H2ColumnDefinitionVisitor(h2TypeVisitor)) {
        @Override
        public String getSendCommand() {
          return SEMICOLON;
        }
        
        @Override
        public String getLineFeed() {
         return "\n";
        }
      };
    } else {
      result = new ActionTemplateHelper(new SqlserverTypeVisitor()) {
        @Override
        public String getSendCommand() {
          return GO;
        }
      };
    }
    return result;
  }

}
