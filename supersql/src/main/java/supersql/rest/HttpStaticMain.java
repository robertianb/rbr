package supersql.rest;

import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.StaticHttpHandler;

import supersql.SupersqlProperties;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;

/**
 * Created with IntelliJ IDEA. User: ian Date: 04/02/13 Time: 23:04 To change
 * this template use File | Settings | File Templates.
 */
public class HttpStaticMain
{


  private URI baseURI;

  private static URI getBaseURI(SupersqlProperties properties)
      throws IOException
  {
    
    return UriBuilder.fromUri("http://" + properties.getStaticHost() + "/")
        .port(properties.getStaticPort()).build();
  }

  protected HttpServer startServer(SupersqlProperties properties)
      throws IOException
  {
    System.out.println("Starting grizzly...");
    ResourceConfig rc = new PackagesResourceConfig("supersql.rest");
    baseURI = getBaseURI(properties);
    HttpServer httpServer = GrizzlyServerFactory.createHttpServer(baseURI, rc);
    return httpServer;
  }

  public static void main(String[] args)
      throws IOException
  {

    HttpStaticMain httpStaticMain = new HttpStaticMain();
    httpStaticMain.doMain(args);

  }

  private void doMain(String[] args)
      throws IOException
  {
    SupersqlProperties properties = new SupersqlProperties();
    HttpServer httpServer = startServer(properties);

    httpServer
        .getServerConfiguration()
        .addHttpHandler(new StaticHttpHandler(
                            properties.getResourcePath()),
                        "/");
    System.out.println(String.format("Server started at %s", baseURI));
    System.in.read();
    httpServer.stop();

  }

}
