package org.lenition.osgi.simple.test;

import org.apache.felix.dm.DependencyManager;
import org.apache.felix.scr.annotations.Reference;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.lenition.osgi.simple.PropertiesService;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.fail;

/**
 * Integration test for PropertiesService.
 */
public class PropertiesServiceTest  {

    private final BundleContext context = FrameworkUtil.getBundle(this.getClass()).getBundleContext();
    private CountDownLatch latch;

    @Reference
    private PropertiesService propertiesService;

    @Before
    protected void setUp()  {
        latch = new CountDownLatch(1);
        DependencyManager dm = new DependencyManager(context);
        dm.add(dm.createComponent()
                .setImplementation(this)
                .add(dm.createServiceDependency()
                .setService(PropertiesService.class)));
        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            fail("Service dependencies were not injected.");
        }
    }

    /**
     * Test that service call succeeds.
     * @throws Exception
     */
    @Test
    public void getTest() throws Exception {
        System.out.println(propertiesService.get());
    }
}
