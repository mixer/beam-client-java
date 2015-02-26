package pro.beam.api.response.users;

import java.util.ArrayList;

public class UserSearchResponse extends ArrayList<UserSearchResponse.User> {
    public static class User {
        public String username;
        public int id;
    }
}
