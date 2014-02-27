package supersql.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import supersql.CrebasScriptProducer;
import supersql.SuperSqlEvents;
import supersql.TranslateScriptProducer;
import supersql.sql.templates.factory.Options;

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
	@Path("/translate")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postTranslate(@FormParam("vendor") String vendor,
			@FormParam("component") String component,
			@FormParam("prev") String prev, @FormParam("next") String next,
			@FormParam("script") String script, @FormParam("check") String check)
	{

		// log.info("Alter request from " + request.getRemoteUser() +
		// " on " + request.getRemoteAddr() + "(" + request.getRemoteHost() +
		// ")");
		log.info("Translate Request for " + vendor + " script (" + prev
				+ " to " + next + ") with check[" + check + "]");
		SuperSqlEvents.getInstance().clear();
		Options outputOptions = new Options(Boolean.valueOf(check), component);
		TranslateScriptProducer crebasScriptProducer = new TranslateScriptProducer(
				outputOptions, vendor, prev, next, script);
		return crebasScriptProducer.produce();
	}

	@POST
	@Produces("text/plain")
	@Path("/alter")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postAlter(@FormParam("vendor") String vendor,
			@FormParam("component") String component,
			@FormParam("prev") String prev, @FormParam("next") String next,
			@FormParam("crebas1") String crebas1,
			@FormParam("crebas2") String crebas2,
			@FormParam("check") String check)
	{
		// log.info("Alter request from " + request.getRemoteUser() +
		// " on " + request.getRemoteAddr() + "(" + request.getRemoteHost() +
		// ")");
		log.info("Alter Request for " + vendor + " script (" + prev + " to "
				+ next + ") with check[" + check + "]");
		return produceScripts(vendor, component, prev, next, crebas1, crebas2,
				"on".equals(check));
	}

	private String produceScripts(String vendor, String component, String prev,
			String next, String crebas1, String crebas2, boolean check)
	{
		SuperSqlEvents.getInstance().clear();
		Options outputOptions = new Options(check, component);
		CrebasScriptProducer crebasScriptProducer = new CrebasScriptProducer(
				outputOptions, vendor, prev, next, crebas1, crebas2);
		return crebasScriptProducer.produce();
	}

	@POST
	@Produces("text/plain")
	@Path("/rollback")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String postRollback(@FormParam("vendor") String vendor,
			@FormParam("component") String component,
			@FormParam("prev") String prev, @FormParam("next") String next,
			@FormParam("crebas1") String crebas1,
			@FormParam("crebas2") String crebas2,
			@FormParam("check") String check)
	{
		log.info("Rollback Request for " + vendor + " script (" + prev + " to "
				+ next + ") with check[" + check + "]");
		return produceScripts(vendor, component, next, prev, crebas2, crebas1,
				"on".equals(check));
	}

}
