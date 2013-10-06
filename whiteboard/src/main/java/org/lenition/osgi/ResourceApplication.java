package org.lenition.osgi;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.core.Application;
import java.util.Set;

@Service(Application.class)
@Component
@Properties({
        @Property(name = "alias", value = "/root")
})
public class ResourceApplication extends Application {

    private static final Set<Class<?>> CLASSES  = new java.util.HashSet<Class<?>>();

    static {
        CLASSES.add(Resource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return CLASSES;
    }
}
