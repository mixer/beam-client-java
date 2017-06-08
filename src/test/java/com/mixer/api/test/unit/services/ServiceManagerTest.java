package com.mixer.api.test.unit.services;

import com.mixer.api.services.AbstractService;
import com.mixer.api.services.ServiceManager;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

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
