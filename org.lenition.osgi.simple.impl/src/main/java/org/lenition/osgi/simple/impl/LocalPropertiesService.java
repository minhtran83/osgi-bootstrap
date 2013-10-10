package org.lenition.osgi.simple.impl;

import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.ReferenceCardinality;
import org.apache.felix.scr.annotations.ReferencePolicy;
import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Function;
import org.lenition.osgi.simple.PropertiesService;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A method for providing a shell command.
 * See http://felix.apache.org/site/rfc-147-overview.html
 * and
 * http://felix.apache.org/site/apache-felix-dependency-manager-using-annotations-quick-tour.html
 *
 *
 * Alternatively, one can create a simple command class for the felix shell
 * (org.apache.felix/org.apache.felix.shell/x.x.x). This shell displays
 * as a Felix WebConsole tab, and is separate from the GoGo or Karaf shells.
 * see http://felix.apache.org/site/apache-felix-shell.html
 *
 */
@Component
@Service
@Properties({
        @Property(name = CommandProcessor.COMMAND_SCOPE, value = "simple"),
        @Property(name = CommandProcessor.COMMAND_FUNCTION, value = { "property", "map", "configurations" })
})
public final class LocalPropertiesService implements PropertiesService {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Reference(cardinality = ReferenceCardinality.MANDATORY_UNARY, policy = ReferencePolicy.DYNAMIC)
    private ConfigurationAdmin configurationAdmin;

    /**
     * {@inheritDoc}
     */
    public Map<Object, Object> get() {
        java.util.Properties properties = System.getProperties();
        properties.put("request.time", asISO8601(new Date()));
        return properties;
    }

    /**
     * Property method.
     * Example: <code>property os.name</code>
     * @param args    command line arguments
     * @throws Exception
     */
    @Descriptor("Print a system property")
    public void property(String[] args) throws Exception {
        for (String arg : args) {
            System.out.println(this.get().get(arg));
        }
    }

    /**
     * Config command.
     * Example: <code>config</code>
     * @throws Exception
     */
    @Descriptor("Print configurations")
    public void configurations() throws Exception {
        if (this.configurationAdmin == null) {
            System.out.println("No ConfigurationAdmin service found.");
        } else {
            System.out.println("====== Configurations ======");
            StringBuilder sb = new StringBuilder();
            for (Configuration configuration : this.configurationAdmin.listConfigurations(null)) {
                sb.append(configuration.getPid() + " : " + configuration.getBundleLocation() + "\n");
                for (String key : Collections.list(configuration.getProperties().keys())) {
                    sb.append("           " + key + " : " + configuration.getProperties().get(key) + "\n");
                }
            }
            System.out.print(sb);
        }
    }


    /**
     * Utility command.
     * Example: <code>simple:map [Yes No Maybe] { echo $it }</code>
     * See POSIX-style commands in <a href="http://svn.apache.org/repos/asf/felix/releases/org.apache.felix.gogo.shell-0.10.0/src/main/java/org/apache/felix/gogo/shell/Posix.java">org.apache.felix.gogo.shell</a>
     * @param session CommandSession hook
     * @param list    Parameters
     * @param closure Function to map to elements
     * @throws Exception
     */
    @Descriptor("Map a function to a list")
    public void map(CommandSession session, Collection<Object> list, Function closure) throws Exception {
        List<Object> args = new ArrayList<Object>();
        args.add(null);
        for (Object x : list) {
            checkInterrupt();
            args.set(0, x);
            closure.execute(session, args);
        }
    }

    private void printAllProperties(PrintStream out) {
        out.println("-------System properties------");
        for (Object key : System.getProperties().keySet()) {
            out.println(key.toString() + "=" + System.getProperty(key.toString()));
        }
    }

    /**
     * Convert java.util.Date to ISO 8601 string.
     * Usage: <code>asISO8601(new Date());</code>
     * @param date date to convert
     * @return ISO 8601 compliant data string in UTC
     */
    private String asISO8601(Date date) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        return df.format(date);
    }

    /**
     * Throw execption if thread is interrupted.
     * @throws InterruptedException
     */
    private void checkInterrupt() throws InterruptedException {
        if (Thread.currentThread().isInterrupted()) {
            throw new InterruptedException("loop interrupted");
        }
    }
}
