package com.mixer.api.test.unit.services;

import com.mixer.api.MixerAPI;
import com.mixer.api.services.AbstractHTTPService;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

public class AbstractHTTPServiceTest {
    @Test public void itFormatsRelativePathNames() {
        MixerAPI mixer = new MixerAPI(URI.create("http://localhost:1337/api/v1/"), "oauthToken");
        AbstractHTTPService httpService = new SimpleHTTPService(mixer, "foo");

        String path = httpService.path("bar");

        Assert.assertEquals("http://localhost:1337/api/v1/foo/bar", path);
    }

    @Test public void itRelativizesAbsolutePaths() {
        MixerAPI mixer = new MixerAPI(URI.create("http://localhost:1337/api/v1/"), "oauthToken");
        AbstractHTTPService httpService = new SimpleHTTPService(mixer, "foo");

        String path = httpService.path("/bar");

        Assert.assertEquals("http://localhost:1337/api/v1/foo/bar", path);
    }

    class SimpleHTTPService extends AbstractHTTPService {
        public SimpleHTTPService(MixerAPI mixer, String path) {
            super(mixer, path);
        }
    }
}
