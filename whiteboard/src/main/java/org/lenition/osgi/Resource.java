package org.lenition.osgi;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

/**
 * Registered with whiteboard enabled HTTP service.
 *
 * See alternative examples:
 * http://svn.apache.org/repos/asf/felix/trunk/http/samples/whiteboard/src/main/java/org/apache/felix/http/samples/whiteboard/Activator.java
 * Using Amdatu: at http://vimeo.com/45035108, 7:00 and 13:30
 * Also see https://source.everit.biz/viewvc/everit-osgi/tags/osgi-0.8.0/samples/jaxrs/
 *
 */
@Path(Resource.CONTEXT)
public class Resource {

	public static final String CONTEXT = "resource";
	
    private static Logger logger = LoggerFactory.getLogger(Resource.class);

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

//    @GET
//    @Produces("application/json")
//    @Path("properties")
//    public String getProperties() throws Exception {
//
//        BundleContext bc = FrameworkUtil.getBundle(Activator.class).getBundleContext();
//        bc.getServiceReference(ConfigurationAdmin.class);
//
//        Configuration c = configurationAdmin.getConfiguration("org.lenition.osgi.whiteboard", null);
//
//        return (String) c.getProperties().get("root");
//    }


    
}
