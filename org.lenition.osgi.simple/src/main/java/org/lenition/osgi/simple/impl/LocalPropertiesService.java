package org.lenition.osgi.simple.impl;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.service.command.CommandProcessor;
import org.apache.felix.service.command.CommandSession;
import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.Function;
import org.lenition.osgi.simple.PropertiesService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Service;
import org.osgi.framework.FrameworkUtil;

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
        @Property(name = CommandProcessor.COMMAND_SCOPE, value = "org.lenition.osgi.simple"),
        @Property(name = CommandProcessor.COMMAND_FUNCTION, value = { "property", "map" })
})
public final class LocalPropertiesService implements PropertiesService {

    private final Map<String, String> result;

    public LocalPropertiesService() {
        BundleContext bundleContext = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
        this.result = new HashMap<String, String>();
        this.result.put("Operating System", bundleContext.getProperty(Constants.FRAMEWORK_OS_NAME) + ' ' + bundleContext.getProperty(Constants.FRAMEWORK_OS_VERSION));
        this.result.put("JVM", System.getProperty("java.vendor") + ' ' + System.getProperty("java.version"));
        this.result.put("Request Time", DateFormat.getInstance().format(new Date()));
    }

    /**
     * {@inheritDoc}
     */
    public Map<String, String> get() {
        this.result.put("Request Time", DateFormat.getInstance().format(new Date()));
        return this.result;
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
     * Utility command.
     * Example: <code>map [Yes No Maybe] { echo $it }</code>
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
