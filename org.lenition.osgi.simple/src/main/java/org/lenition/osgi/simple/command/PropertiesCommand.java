package org.lenition.osgi.simple.command;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.lenition.osgi.simple.PropertiesService;

import java.io.PrintStream;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * A simple command for the felix shell (org.apache.felix/org.apache.felix.shell/x.x.x). This shell displays
 * as a Felix WebConsole tab, and is separate from the GoGo or Karaf shells.
 * see http://felix.apache.org/site/apache-felix-shell.html
 */
@Component
@Service
public class PropertiesCommand implements org.apache.felix.shell.Command {

    private static final String NAME = "property";
    private static final String USAGE = "property <property> [<property> ... ]";
    private static final String SHORT_DESCRIPTION = "Returns a system property value";

    @Reference
    private PropertiesService propertiesService;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getUsage() {
        return USAGE;
    }

    @Override
    public String getShortDescription() {
        return SHORT_DESCRIPTION;
    }

    @Override
    public void execute(String line, PrintStream out, PrintStream err) {
        StringTokenizer st = new StringTokenizer(line, " ");

        // Ignore the command name.
        st.nextToken();

        if (st.countTokens() >= 1) {
            while (st.hasMoreTokens()) {
                out.println(propertiesService.get().get(st.nextToken()));
            }
        } else {
            Map<String, String> properties = propertiesService.get();
            for (String key : properties.keySet()) {
                out.println(key + " : " + properties.get(key));
            }
        }
    }
}
