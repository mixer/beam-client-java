package com.mixer.api.test.integration;

import com.mixer.api.BeamAPI;
import com.mixer.api.resource.BeamUser;
import com.mixer.api.services.impl.UsersService;
import org.junit.Assert;
import org.junit.Test;

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
