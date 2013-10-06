package org.lenition.osgi;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;

import javax.ws.rs.core.Application;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Service(Application.class)
@Component
@Properties({
        @Property(name = "alias", value = "/root2")
})
public class OtherResourceApplication extends Application {

    private final Set<Class<?>> resources  = new java.util.HashSet<Class<?>>();

    public OtherResourceApplication() {
        resources.add(Resource.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return resources;
    }
}
