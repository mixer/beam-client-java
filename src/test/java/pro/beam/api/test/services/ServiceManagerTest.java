package pro.beam.api.test.services;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import pro.beam.api.services.AbstractService;
import pro.beam.api.services.ServiceManager;

public class ServiceManagerTest {
    @Test public void itRegistersServices() {
        AbstractService serviceStub = Mockito.mock(AbstractService.class);
        ServiceManager<AbstractService> sm = new ServiceManager<>();

        sm.register(serviceStub);

        Assert.assertEquals(sm.get(serviceStub.getClass()), serviceStub);
    }

    @Test public void itUnregistersServices() {
        AbstractService serviceStub = Mockito.mock(AbstractService.class);
        ServiceManager<AbstractService> sm = new ServiceManager<>();

        sm.register(serviceStub);
        sm.unregister(serviceStub);

        Assert.assertNull(sm.get(serviceStub.getClass()));
    }

    @Test public void itUnregistersAllServices() {
        AbstractService serviceStub = Mockito.mock(AbstractService.class);
        ServiceManager<AbstractService> sm = new ServiceManager<>();

        sm.register(serviceStub);
        sm.unregisterAll();

        Assert.assertNull(sm.get(serviceStub.getClass()));
    }
}
