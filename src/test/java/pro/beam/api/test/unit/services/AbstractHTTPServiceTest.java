package pro.beam.api.test.unit.services;

import org.junit.Assert;
import org.junit.Test;
import pro.beam.api.BeamAPI;
import pro.beam.api.services.AbstractHTTPService;

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
