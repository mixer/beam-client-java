package pro.beam.api.test.integration;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.junit.Test;
import org.junit.Assert;
import pro.beam.api.BeamAPI;
import pro.beam.api.resource.BeamUser;
import pro.beam.api.services.impl.UsersService;

import java.util.concurrent.ExecutionException;


/**
 * Created by admin on 08/08/2016.
 */
public class UserService {

    @Test
    public void itAllowsUsToGetTheCurrentUser() throws ExecutionException, InterruptedException {
        BeamAPI beam = new BeamAPI(null, "Merlin", "sdfsdfds");
        BeamUser user = beam.use(UsersService.class).getCurrent().get();
        Assert.assertEquals(user.username, "Merlin");
    }
}
