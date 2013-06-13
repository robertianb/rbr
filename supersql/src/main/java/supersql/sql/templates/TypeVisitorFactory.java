package supersql.sql.templates;

import supersql.ast.types.TypeVisitor;
import supersql.sql.templates.mysql.MySqlTypeVisitor;
import supersql.sql.templates.oracle.OracleTypeVisitor;
import supersql.sql.templates.sqlserver.SqlserverTypeVisitor;
import supersql.sql.templates.sybase.SybaseTypeVisitor;

public class TypeVisitorFactory
{
  
  public TypeVisitorFactory() {
  }

  public TypeVisitor createTypeVisitor(String dbVendor)
  {
    if (Vendor.SQLSERVER.equals(dbVendor))
    {
      return new SqlserverTypeVisitor();
    } else if (Vendor.ORACLE.equals(dbVendor))
    {
      return new OracleTypeVisitor();
    } else if (Vendor.SYBASE.equals(dbVendor)){
      return new SybaseTypeVisitor(); 
    } else if (Vendor.MYSQL.equals(dbVendor)){
      return new MySqlTypeVisitor(); 
    } else if (Vendor.SUMMARY.equals(dbVendor)) {
        return new OracleTypeVisitor();
    } else{
      throw new RuntimeException("Unsupported DB vendor : " + dbVendor);
    }
  }
}
