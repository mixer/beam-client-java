package com.mixer.api.test.integration;

import com.mixer.api.MixerAPI;
import com.mixer.api.resource.MixerUser;
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
        MixerAPI mixer = new MixerAPI(null, "Merlin", "sdfsdfds");
        MixerUser user = mixer.use(UsersService.class).getCurrent().get();
        Assert.assertEquals(user.username, "Merlin");
    }
}
