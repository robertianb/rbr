package supersql.sql.templates;

import supersql.ast.types.TypeVisitor;
import supersql.sql.templates.h2.H2TypeVisitor;
import supersql.sql.templates.oracle.OracleTypeVisitor;
import supersql.sql.templates.sqlserver.SqlserverTypeVisitor;
import supersql.sql.templates.sybase.SybaseTypeVisitor;

public class TypeVisitorFactory
{
  
  public TypeVisitorFactory() {
  }

  public TypeVisitor createTypeVisitor(String dbVendor)
  {
    if ("sqlserver".equals(dbVendor))
    {
      return new SqlserverTypeVisitor();
    } else if ("oracle".equals(dbVendor))
    {
      return new OracleTypeVisitor();
    } else if ("sybase".equals(dbVendor)){
      return new SybaseTypeVisitor(); 
    } else if (Vendor.SUMMARY.equals(dbVendor)) {
        return new OracleTypeVisitor();
    } else if (Vendor.H2.equals(dbVendor)) {
      return new H2TypeVisitor();
    } else{
      throw new RuntimeException("Unsupported DB vendor : " + dbVendor);
    }
  }
}
