package org.lenition.osgi;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

public class Configuration implements ManagedService {

    static final String CONFIG_SERVICE_PID = "org.lenition.osgi.whiteboard";
    static final String ROOT_PROPERTY = "root";

    public Configuration(  ) {
    }

    @Override
    public void updated( Dictionary<String, ?> properties ) throws ConfigurationException {
        if( properties != null ) {
            Object root = properties.get( ROOT_PROPERTY );
            ensureRootIsPresent( root );
            String rootPath = ( String )root;
            ensureRootIsValid( rootPath );
//            application.updatePath( rootPath );
        }
    }

    private void ensureRootIsValid( String rootPath ) throws ConfigurationException {
        if( !rootPath.startsWith( "/" ) ) {
            throw new ConfigurationException( ROOT_PROPERTY, "Root path does not start with a /" );
        }
    }

    private void ensureRootIsPresent( Object root ) throws ConfigurationException {
        if( root == null || !( root instanceof String ) ) {
            throw new ConfigurationException( ROOT_PROPERTY, "Property is not set or invalid." );
        }
    }
}