package org.lenition.osgi;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.core.Application;
import java.util.Set;

/**
 * JAX RS Application used as a container for provider classes.
 */
@Service(Application.class)
@Component
public class ConfiguredResourceApplication extends Application {

    private final Set<Class<?>> resources  = new java.util.HashSet<Class<?>>();

    /**
     * Default constructor.
     */
    public ConfiguredResourceApplication() {
        resources.add(Resource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return resources;
    }
}
