package com.mixer.api.test.unit.services;

import com.mixer.api.BeamAPI;
import com.mixer.api.services.AbstractHTTPService;
import org.junit.Assert;
import org.junit.Test;

import java.net.URI;

public class AbstractHTTPServiceTest {
    @Test public void itFormatsRelativePathNames() {
        BeamAPI beam = new BeamAPI(URI.create("http://localhost:1337/api/v1/"), "username", "password");
        AbstractHTTPService httpService = new SimpleHTTPService(beam, "foo");

        String path = httpService.path("bar");

        Assert.assertEquals("http://localhost:1337/api/v1/foo/bar", path);
    }

    @Test public void itRelativizesAbsolutePaths() {
        BeamAPI beam = new BeamAPI(URI.create("http://localhost:1337/api/v1/"), "username", "password");
        AbstractHTTPService httpService = new SimpleHTTPService(beam, "foo");

        String path = httpService.path("/bar");

        Assert.assertEquals("http://localhost:1337/api/v1/foo/bar", path);
    }

    class SimpleHTTPService extends AbstractHTTPService {
        public SimpleHTTPService(BeamAPI beam, String path) {
            super(beam, path);
        }
    }
}
