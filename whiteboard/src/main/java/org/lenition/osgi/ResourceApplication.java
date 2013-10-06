package org.lenition.osgi;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * JAX RS Application used as a container for provider classes.
 */
@Service(Application.class)
@Component
@Properties({
        @Property(name = "alias", value = "/root")
})
public class ResourceApplication extends Application {

    private final Set<Class<?>> resources  = new java.util.HashSet<Class<?>>();

    /**
     * Default constructor.
     */
    public ResourceApplication() {
        resources.add(Resource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return resources;
    }
}
