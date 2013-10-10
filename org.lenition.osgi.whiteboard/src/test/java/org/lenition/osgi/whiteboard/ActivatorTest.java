package org.lenition.osgi.whiteboard;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Dictionary;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.ManagedService;

/**
 * Test for Bundle Activator class.
 */
@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ActivatorTest {

    private Activator activator;

    @Mock
    private BundleContext context;

    @Mock
    private ServiceRegistration connectorRegistration;

    @Mock
    private ServiceRegistration configRegistration;

    @Mock
    private Bundle jerseyServer;

    /**
     * Setup tests.
     * @throws InvalidSyntaxException
     */
    @Before
    public void setUp() throws InvalidSyntaxException {
        Activator original = new Activator();
        activator = spy(original);
        Filter filter = mock(Filter.class);
        when(context.createFilter(anyString())).thenReturn(filter);
        when(context.registerService(eq(ManagedService.class.getName()),
                anyObject(),
                any(Dictionary.class))).thenReturn(configRegistration);
    }

    /**
     * Test registering configuration service.
     * @throws Exception
     */
    @Test
    public void testRegisterService() throws Exception {
        activator.start(context);
        verify(context).registerService(eq(ManagedService.class.getName()),
                any(Configuration.class),
                any(Dictionary.class));
    }

    /**
     * Test deregistering configuration service.
     * @throws Exception
     */
    @Test
    public void testDeregisterService() throws Exception {
        activator.start(context);
        activator.stop(context);
        verify(configRegistration).unregister();
    }

}
