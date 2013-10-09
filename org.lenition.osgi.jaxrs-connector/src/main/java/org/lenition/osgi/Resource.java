package org.lenition.osgi;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Collections;
import java.util.Dictionary;

/**
 * Registered with osgi-jaxrs-connector.
 *
 * osgi-jaxrs-connector causes an intermittent start order dependencies. If the resource isn't
 * activated when jax-rs starts, it will cause an error:
 *
 * [FelixDispatchQueue] DEBUG com.eclipsesource.jaxrs.connector - FrameworkEvent ERROR - com.eclipsesource.jaxrs.connector
 java.lang.NullPointerException
 at com.sun.jersey.spi.container.servlet.ServletContainer.reload(ServletContainer.java:515)
 at com.eclipsesource.jaxrs.connector.internal.JerseyContext.addResource(JerseyContext.java:47)
 at com.eclipsesource.jaxrs.connector.internal.JAXRSConnector.registerResource(JAXRSConnector.java:137)
 at com.eclipsesource.jaxrs.connector.internal.JAXRSConnector.registerResource(JAXRSConnector.java:119)
 at com.eclipsesource.jaxrs.connector.internal.JAXRSConnector.clearCache(JAXRSConnector.java:80)
 at com.eclipsesource.jaxrs.connector.internal.JAXRSConnector.doAddHttpService(JAXRSConnector.java:72)
 at com.eclipsesource.jaxrs.connector.internal.JAXRSConnector.addHttpService(JAXRSConnector.java:64)
 at com.eclipsesource.jaxrs.connector.internal.HttpTracker.addingService(HttpTracker.java:31)
 at com.eclipsesource.jaxrs.connector.internal.HttpTracker.addingService(HttpTracker.java:1)

 which should instead be:
 [FelixStartLevel] DEBUG osgi-bootstrap.jaxrs-connector - ServiceEvent REGISTERED - [org.lenition.osgi.Resource] - osgi-bootstrap.jaxrs-connector
 [FelixDispatchQueue] DEBUG osgi-bootstrap.jaxrs-connector - BundleEvent STARTED - osgi-bootstrap.jaxrs-connector

 then:
 [CM Configuration Updater (Update: pid=com.eclipsesource.jaxrs.connector)] DEBUG org.ops4j.pax.web.service.internal.HttpServiceProxy - Registering servlet: [/ro
 ot] -> com.sun.jersey.spi.container.servlet.ServletContainer@6d91ef32
 [CM Configuration Updater (Update: pid=com.eclipsesource.jaxrs.connector)] DEBUG org.ops4j.pax.web.service.internal.HttpServiceStarted - Using context [ContextM
 odel{id=org.ops4j.pax.web.service.spi.model.ContextModel-6,name=,httpContext=DefaultHttpContext{bundle=com.eclipsesource.jaxrs.connector [5]},contextParams={}}]

 [CM Configuration Updater (Update: pid=com.eclipsesource.jaxrs.connector)] DEBUG org.ops4j.pax.web.service.jetty.internal.JettyServerImpl - Adding servlet [Serv
 letModel{id=org.ops4j.pax.web.service.spi.model.ServletModel-7,name=org.ops4j.pax.web.service.spi.model.ServletModel-7,urlPatterns=[/root/*],alias=/root,servlet
 =com.sun.jersey.spi.container.servlet.ServletContainer@6d91ef32,initParams={},context=ContextModel{id=org.ops4j.pax.web.service.spi.model.ContextModel-6,name=,h
 ttpContext=DefaultHttpContext{bundle=com.eclipsesource.jaxrs.connector [5]},contextParams={}}}]
 [CM Configuration Updater (Update: pid=com.eclipsesource.jaxrs.connector)] DEBUG org.eclipse.jetty.util.log - Container org.ops4j.pax.web.service.jetty.internal
 .JettyServerHandlerCollection@35ead007 + HttpServiceContext{httpContext=null} as handler
 */
@Component
@Service(Resource.class)
@Path(Resource.CONTEXT)
public class Resource {

    /**
     * Path for this resource.
     */
    public static final String CONTEXT = "resource";
    private static Logger logger = LoggerFactory.getLogger(Resource.class);

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY)
    private ConfigurationAdmin configurationAdmin;

    /**
     * Default (root) GET.
     * @return HTTP response string
     * @throws Exception
     */
    @GET
    @Produces("application/json")
    public String getDefault() throws Exception {
        logger.info("getDefault() called.");

        URL url = Resources.getResource(Resource.class, "/success.json");
        /*
         equivalent to both of:
             url = getClass().getResource("/success.json");
             url = getClass().getClassLoader().getResource("success.json");
         */
        String text = Resources.toString(url, Charsets.UTF_8);
        return text;
    }

    /**
     * GET method for inspecting properties .
     * @return HTTP response string
     * @throws Exception
     */
    @GET
    @Path("config")
    @Produces("application/json")
    public String getConfig() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Configuration configuration : this.configurationAdmin.listConfigurations(null)) {
            sb.append(configuration.getPid() + " : " + configuration.getBundleLocation() + "\n");
            for (String key : Collections.list(configuration.getProperties().keys())) {
                sb.append("           " + key + " : " + configuration.getProperties().get(key) + "\n");
            }
        }
        return sb.toString();
    }

    /**
     * Pretty print method for dictionary.
     * @param dictionary dictionary to print
     * @param <K> key type
     * @param <V> value type
     * @return pretty printed string
     */
    public static <K, V> String prettyPrint(Dictionary<K, V> dictionary) {
        if (dictionary == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder("[");
        int i = 0;
        for (K key : Collections.list(dictionary.keys())) {
            sb.append(key + "=" + dictionary.get(key));
            sb.append(",\n");
        }
        sb.append("]");
        return sb.toString();
    }

}
