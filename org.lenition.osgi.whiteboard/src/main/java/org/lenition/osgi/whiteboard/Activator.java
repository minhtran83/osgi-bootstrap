package org.lenition.osgi.whiteboard;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.ws.rs.core.Application;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;

/**
 * Activator class. Registers ServiceTracker and Configuration
 */
public class Activator implements BundleActivator {

    private static Logger logger = LoggerFactory.getLogger(Activator.class);

    private BundleContext context;
    private ServiceTracker tracker;
    private ServiceRegistration configRegistration;

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public void start(BundleContext context) throws Exception {
        logger.info("Activator.start() called.");

        // chosen so that the JAX-RS 2.0 implementation of UriBuilder
        // (org.glassfish.jersey.server.internal.RuntimeDelegateImpl) is used
        // instead of com.sun.jersey.apu.uri.UriBuilderImpl
        System.setProperty("javax.ws.rs.ext.RuntimeDelegate",
                "org.glassfish.jersey.server.internal.RuntimeDelegateImpl");

        this.context = context;
        registerConfiguration(context);

        //Track all JAX-RS Applications
        this.tracker = new ServiceTracker(context, Application.class.getName(), new Customizer());
        this.tracker.open();
    }

    /** {@inheritDoc} */
    @Override
    public void stop(BundleContext context) throws Exception {
        logger.info("Activator.stop() called.");
        this.tracker.close();
        this.tracker = null;
        this.context = null;
        configRegistration.unregister();
        configRegistration = null;
    }

    /**
     * Binds configuration to bundle.
     * @param context
     */
    private void registerConfiguration(BundleContext context) {
        Dictionary<String, String> properties = new Hashtable<String, String>();
        properties.put(Constants.SERVICE_PID, Configuration.CONFIG_SERVICE_PID);
        configRegistration = context.registerService(ManagedService.class.getName(), new Configuration(), properties);
    }

    private class Customizer implements ServiceTrackerCustomizer {

        private Dictionary<String, String> createProps(ServiceReference reference) {
            Dictionary<String, String> props = new Hashtable<String, String>();
            String alias = null;
            if (reference.getProperty("alias") != null) {
                alias = reference.getProperty("alias").toString();
            } else {
                try {
                    ServiceReference ref = context.getServiceReference(ConfigurationAdmin.class);
                    ConfigurationAdmin configurationAdmin = (ConfigurationAdmin)context.getService(ref);
                    org.osgi.service.cm.Configuration config = configurationAdmin.getConfiguration(Configuration.CONFIG_SERVICE_PID);
                    alias = config.getProperties().get(Configuration.ROOT_PROPERTY).toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.debug("Alias: " + alias);
            props.put("alias", alias);
            return props;
        }

        /** {@inheritDoc} */
        @Override
        public Object addingService(ServiceReference reference) {
            Application app = (Application)context.getService(reference);
            logger.debug("Adding JAX-RS application: " + app);

            //For each JAX-RS Application, create a servlet wrapping that Application instance
            ServletContainer servlet = new ServletContainer(app);

            Bundle sourceBundle = reference.getBundle();
            BundleContext sourceContext = sourceBundle.getBundleContext();
            return sourceContext.registerService(Servlet.class.getName(), servlet, createProps(reference));
        }

        /** {@inheritDoc} */
        @Override
        @SuppressWarnings("unchecked")
        public void modifiedService(ServiceReference reference, Object service) {
            ServiceRegistration reg = (ServiceRegistration)service;
            logger.debug("Modifying JAX-RS application: " + reg);
            reg.setProperties(createProps(reference));
        }

        /** {@inheritDoc} */
        @Override
        public void removedService(ServiceReference reference, Object service) {
            ServiceRegistration reg = (ServiceRegistration)service;
            logger.debug("Removing JAX-RS application: " + reg);
            reg.unregister();
            context.ungetService(reference);
        }

    }

}
