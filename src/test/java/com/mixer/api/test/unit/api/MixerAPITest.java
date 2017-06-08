package com.mixer.api.test.unit.api;

import com.mixer.api.MixerAPI;
import com.mixer.api.services.AbstractMixerService;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.net.URI;

public class MixerAPITest {
    @Test
    public void itConstructsWithoutParameters() {
        MixerAPI mixer = new MixerAPI();

        Assert.assertEquals(mixer.basePath, URI.create("https://mixer.com/api/v1/"));
        Assert.assertNotNull(mixer.gson);
        Assert.assertNotNull(mixer.executor);
        Assert.assertNotNull(mixer.http);
    }

    @Test
    public void itConstructsWithParameters() {
        URI basePath = URI.create("https://localhost:1337/api/v1/");
        MixerAPI mixer = new MixerAPI(basePath, "oauthToken");

        Assert.assertEquals(mixer.basePath, basePath);
    }

    @Test public void itDelegatesToTheServiceManager() {
        AbstractMixerService service = Mockito.mock(AbstractMixerService.class);
        MixerAPI mixer = new MixerAPI();

        mixer.register(service);

        Assert.assertEquals(mixer.use(service.getClass()), service);
    }
}
