package org.lenition.osgi;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Registered with osgi-jaxrs-connector.
 * Alternatively, use the whiteboard pattern http://svn.apache.org/repos/asf/felix/trunk/http/samples/whiteboard/src/main/java/org/apache/felix/http/samples/whiteboard/Activator.java
 * Another whiteboard example using DependencyActivatorBase at http://vimeo.com/45035108, 7:00 and 13:30
 *
 * Also see https://source.everit.biz/viewvc/everit-osgi/tags/osgi-0.8.0/samples/jaxrs/
 *
 * osgi-jaxrs-connector causes an intermittent start order dependencies. If the resource isn't activated when jax-rs starts, it will cause an error:
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

	public static final String CONTEXT = "resource";
	
    private static Logger logger = LoggerFactory.getLogger(Resource.class);

//	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.OPTIONAL_UNARY)

	@GET
	@Produces("application/json")
	public String getDefault() throws Exception {
        logger.info("getDefault() called.");
        // TODO: get resource from local classpath

        /*

        'com.google.guava:guava:15.0'
        scan-bundle:mvn:com.google.guava/guava/15.0@20

        URL url = null;

        url = getClass().getResource("/success.json");
        logger.info( Resources.toString(url, Charsets.UTF_8));

        url = getClass().getClassLoader().getResource("success.json");
        logger.info( Resources.toString(url, Charsets.UTF_8));

        url = Resources.getResource(Resource.class, "success.json");
        String text = Resources.toString(url, Charsets.UTF_8);
        */
		return (new Gson()).toJson("success");
	}



	// ----------------------------------------------------------------------
    // bind/unbind methods for Declarative Services
    // ----------------------------------------------------------------------
//    public synchronized void bindRpcService(RpcService svc) {
//        rpcService = svc;
//    }
//
//    public synchronized void unbindRpcService(RpcService svc) {
//        if (svc == rpcService)
//            rpcService = null;
//    }
        
    
}
