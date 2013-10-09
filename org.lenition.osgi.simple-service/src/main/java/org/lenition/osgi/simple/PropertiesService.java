package org.lenition.osgi.simple;

import java.util.Map;

/**
 * Simple service to return a systems properties map.
 */
public interface PropertiesService {

    /**
     * Return a map of system properties.
     * @return map of system property names (keys) and values
     */
    Map<String, String> get();
}
