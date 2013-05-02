package supersql.sql.templates;

import supersql.sql.templates.oracle.OracleTypeVisitor;
import supersql.sql.templates.sqlserver.SqlserverTypeVisitor;
import supersql.sql.templates.sybase.SybaseTypeVisitor;

// TODO handle all specific stuff 
public class ActionTemplateHelperFactory
{
  
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
          return "go";
        }
        
        @Override
        public String getLineFeed() {
          return "+";
        }
      };
    } else if (Vendor.SYBASE.equals(vendor))
    {
      result = new ActionTemplateHelper(new SybaseTypeVisitor()) {
        @Override
        public String getSendCommand() {
          return "go";
        }
      };
    } else {
      result = new ActionTemplateHelper(new SqlserverTypeVisitor()) {
        @Override
        public String getSendCommand() {
          return "go";
        }
      };
    }
    return result;
  }

}
