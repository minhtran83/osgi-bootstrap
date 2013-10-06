package org.lenition.osgi;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.Constants;

import javax.ws.rs.core.Application;
import java.util.Set;

@Service(Application.class)
@Component
@Properties({
        @Property(name = "alias", value = "/root")
})
public class ResourceApplication extends Application {

    private final Set<Class<?>> resources  = new java.util.HashSet<Class<?>>();

    public ResourceApplication() {
        resources.add(Resource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return resources;
    }
}
