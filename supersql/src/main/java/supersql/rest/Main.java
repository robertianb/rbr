package supersql.rest;



import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;

import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import supersql.SupersqlProperties;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 01/02/13
 * Time: 08:31
 * To change this template use File | Settings | File Templates.
 */
public class Main {


    public static URI BASE_URI;

    static {
        try {
            BASE_URI = getBaseURI();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static URI getBaseURI() throws IOException {
            SupersqlProperties properties = new SupersqlProperties();
            return UriBuilder.fromUri("http://" + properties.getRestHost() + "/").port(properties.getRestPort()).build();
        }

        protected static HttpServer startServer() throws IOException
        {
            System.out.println("Starting grizzly...");
            ResourceConfig rc = new PackagesResourceConfig("supersql.rest");
            HttpServer httpServer = GrizzlyServerFactory.createHttpServer(BASE_URI, rc);
            return httpServer;
        }

        public static void main(String[] args) throws IOException
        {
            HttpServer httpServer = startServer();

            //httpServer.getServerConfiguration().addHttpHandler(new StaticHttpHandler("/Users/ian/IdeaProjects/supersql/src/main/resources"),"/");
            System.out
                    .println(String
                            .format("Jersey app started with WADL available at "
                                    + "%sapplication.wadl\nTry out %screbas2alter\nHit enter to stop it...",
                                    BASE_URI, BASE_URI));
            System.in.read();
            httpServer.stop();
        }

    }


