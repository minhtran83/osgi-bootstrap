package org.lenition.test;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Registered with osgi-jaxrs-connector.
 * Alternatively, use the whiteboard pattern http://svn.apache.org/repos/asf/felix/trunk/http/samples/whiteboard/src/main/java/org/apache/felix/http/samples/whiteboard/Activator.java
 * Another whiteboard example using DependencyActivatorBase at http://vimeo.com/45035108, 7:00 and 13:30
 *
 * Also see https://source.everit.biz/viewvc/everit-osgi/tags/osgi-0.8.0/samples/jaxrs/
 */
@Component
@Service(TestResource.class)
@Path(TestResource.CONTEXT)
public class TestResource {

	public static final String CONTEXT = "test";
	
    private static Logger logger = LoggerFactory.getLogger(TestResource.class);

//	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.OPTIONAL_UNARY)

	@GET
	@Produces("application/json")
	public String getDefault() throws Exception {
        logger.info("getDefault() called.");
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
