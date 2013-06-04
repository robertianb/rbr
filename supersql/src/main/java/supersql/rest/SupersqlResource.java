package supersql.rest;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import supersql.SuperSqlEvents;
import supersql.ast.actions.ActionCodes;
import supersql.ast.types.TypeVisitor;
import supersql.diff.CrebasComparator;
import supersql.sql.templates.ActionTemplateHelperFactory;
import supersql.sql.templates.CreateTableTemplateFactory;
import supersql.sql.templates.TemplateScriptVisitor;
import supersql.sql.templates.TypeVisitorFactory;
import supersql.sql.templates.UpgradeVersionTemplateFactory;
import supersql.sql.templates.Vendor;

/**
 * Created with IntelliJ IDEA. User: ian Date: 01/02/13 Time: 08:35 To change
 * this template use File | Settings | File Templates.
 */
@Path("/crebas2alter")
public class SupersqlResource
{

  public static final Logger log = Logger.getLogger(SupersqlResource.class);

  @POST
  @Produces("text/plain")
  @Path("/alter")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String postAlter(@FormParam("vendor")
  String vendor, @FormParam("component")
  String component, @FormParam("prev")
  String prev, @FormParam("next")
  String next, @FormParam("crebas1")
  String crebas1, @FormParam("crebas2")
  String crebas2)
  {
    // log.info("Alter request from " + request.getRemoteUser() +
    // " on " + request.getRemoteAddr() + "(" + request.getRemoteHost() + ")");
    log.info("Alter Request for " + vendor + " script (" + prev + " to " + next
        + ")");
    return producScripts(vendor, component, prev, next, crebas1, crebas2);
  }

  private String producScripts(String vendor, String component, String prev,
                               String next, String crebas1, String crebas2)
  {
    SuperSqlEvents.getInstance().clear();
    StringBuffer sb = new StringBuffer();
    try {
      if (vendor.equalsIgnoreCase("All")) {

        for (String v : Vendor.ALL) {
          sb.append("-- ############################### " + v
              + " ############################### \n");
          sb.append(produceAlterScript(v, component,
                                       new CrebasComparator(new StringReader(
                                           crebas1), new StringReader(crebas2),
                                           prev, next),
                                       new TypeVisitorFactory(),
                                       new ActionTemplateHelperFactory()));
        }
      }
      else {
        sb.append(produceAlterScript(vendor, component,
                                     new CrebasComparator(new StringReader(
                                         crebas1), new StringReader(crebas2),
                                         prev, next),
                                     new TypeVisitorFactory(),
                                     new ActionTemplateHelperFactory()));
      }
    }
    catch (Exception e) {
      StringWriter sw = new StringWriter();
      PrintWriter p = new PrintWriter(sw);
      sb.append(SuperSqlEvents.getInstance().getErrorSummary());
      e.printStackTrace(p);
      sb.append('\n');
      sb.append(sw.getBuffer());
    }
    if (SuperSqlEvents.getInstance().hasErrors()) {
      log.warn(SuperSqlEvents.getInstance().getErrorSummary());
      FileWriter fileWriter1 = null;
      FileWriter fileWriter2 = null;
      try {
        fileWriter1 = new FileWriter("crebas1."
            + new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date())
            + ".sql");
        fileWriter1.append(crebas1);
        fileWriter1.flush();

        fileWriter2 = new FileWriter("crebas2."
            + new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date())
            + ".sql");
        fileWriter2.append(crebas2);
        fileWriter2.flush();

      }
      catch (IOException e) {
        log.error(e);
      }
      finally {
        try {
          fileWriter1.close();
          fileWriter2.close();
        }
        catch (Exception e) {
          log.error(e);
        }
      }
    }
    return sb.toString();
  }

  @POST
  @Produces("text/plain")
  @Path("/rollback")
  @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
  public String postRollback(@FormParam("vendor")
  String vendor, @FormParam("component")
  String component, @FormParam("prev")
  String prev, @FormParam("next")
  String next, @FormParam("crebas1")
  String crebas1, @FormParam("crebas2")
  String crebas2)
  {
    log.info("Rollback Request for " + vendor + " script (" + prev + " to " + next
        + ")");
    return producScripts(vendor, component, next, prev, crebas2, crebas1);
  }

  private String produceAlterScript(String vendor, String component,
                                    CrebasComparator crebasComparator,
                                    TypeVisitorFactory typeVisitorFactory,
                                    ActionTemplateHelperFactory helperFactory)
  {

    TypeVisitor typeVisitor = typeVisitorFactory.createTypeVisitor(vendor);

    TemplateScriptVisitor scriptVisitor = new TemplateScriptVisitor(vendor,
        helperFactory.createHelper(vendor));
    if (!Vendor.SUMMARY.equals(vendor)) {
      // create table template
      scriptVisitor.setActionTemplateFactory(ActionCodes.CREATE_TABLE,
                                             new CreateTableTemplateFactory());
      scriptVisitor.setActionTemplateFactory(ActionCodes.UPGRADE_VERSION,
                                             new UpgradeVersionTemplateFactory(
                                                 component));
    }

    crebasComparator.visit(scriptVisitor);
    return scriptVisitor.getOutput().toString();
  }

}
