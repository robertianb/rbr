package supersql;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: ian
 * Date: 13/02/13
 * Time: 22:23
 * To change this template use File | Settings | File Templates.
 */
public class SupersqlProperties {

    private final Properties properties;

    public SupersqlProperties() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("supersql.properties"));
    }

    public String getStaticHost()
    {
        return properties.getProperty("staticHost");
    }

    public String getRestHost()
    {
        return properties.getProperty("restHost");
    }

    public String getResourcePath()
    {
        return properties.getProperty("resourcePath");
    }

    public int getStaticPort() {
        return Integer.parseInt(properties.getProperty("staticPort"));
    }

    public int getRestPort() {
        return Integer.parseInt(properties.getProperty("restPort"));
    }
}

