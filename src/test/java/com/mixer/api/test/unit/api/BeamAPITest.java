package com.mixer.api.test.unit.api;

import com.mixer.api.BeamAPI;
import com.mixer.api.services.AbstractBeamService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;

public class BeamAPITest {
    @Test
    public void itConstructsWithoutParameters() {
        BeamAPI beam = new BeamAPI();

        Assert.assertEquals(beam.basePath, URI.create("https://beam.pro/api/v1/"));
        Assert.assertNotNull(beam.gson);
        Assert.assertNotNull(beam.executor);
        Assert.assertNotNull(beam.http);
    }

    @Test
    public void itConstructsWithParameters() {
        URI basePath = URI.create("https://localhost:1337/api/v1/");
        BeamAPI beam = new BeamAPI(basePath, "my_username", "my_password");

        Assert.assertEquals(beam.basePath, basePath);
    }

    @Test public void itDelegatesToTheServiceManager() {
        AbstractBeamService service = Mockito.mock(AbstractBeamService.class);
        BeamAPI beam = new BeamAPI();

        beam.register(service);

        Assert.assertEquals(beam.use(service.getClass()), service);
    }
}
