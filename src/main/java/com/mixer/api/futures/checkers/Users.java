package com.mixer.api.futures.checkers;

import com.google.gson.Gson;
import com.mixer.api.futures.JojenFutureChecker;
import com.mixer.api.resource.MixerUser;

public class Users {
    public static class RegistrationChecker extends JojenFutureChecker<MixerUser> {
        public RegistrationChecker(Gson gson) {
            super(gson);
        }
    }
}
