package org.lenition.osgi.whiteboard;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

/**
 * ManagedService for configuration. CONFIG_SERVICE_PID must match the name of
 * a .cfg file in Felix fileinstall directory.
 */
public class Configuration implements ManagedService {

    /**
     * PID of ManagedService.
     */
    static final String CONFIG_SERVICE_PID = "org.lenition.osgi.whiteboard";

    /**
     * Name of root property.
     */
    static final String ROOT_PROPERTY = "root";

    @Override
    public void updated(Dictionary<String, ?> properties) throws ConfigurationException {
        if (properties != null) {
            Object root = properties.get(ROOT_PROPERTY);
            ensureRootIsPresent(root);
            String rootPath = (String)root;
            ensureRootIsValid(rootPath);
        }
    }

    private void ensureRootIsValid(String rootPath) throws ConfigurationException {
        if (!rootPath.startsWith("/")) {
            throw new ConfigurationException(ROOT_PROPERTY, "Root path does not start with a /");
        }
    }

    private void ensureRootIsPresent(Object root) throws ConfigurationException {
        if (root == null || !(root instanceof String)) {
            throw new ConfigurationException(ROOT_PROPERTY, "Property is not set or invalid.");
        }
    }
}
