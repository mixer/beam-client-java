# beam-java

This is the official Java client for the [beam.pro](https://beam.pro) API.  It
currently supports `v1` and is a work in progress.

## Building

To build, execute: `mvn package` in your shell.

## Example:

Here, an example of how to use the built-in Future callback system provided in
Guava follows.  In this example, we'll construct the Beam API, and then get a
list of all channel IDs.

```java
// Construct an instance of the Beam API such that we can query certain
// endpoints for data.
BeamAPI beam = new BeamAPI();

// Invoke the `UsersService.class` in order to access the methods within
// that service.  Then, assign a callback using Guava's FutureCallback
// class so we can act on the response.
Futures.addCallback(beam.use(UsersService.class).search("tta"), new
FutureCallback<UserSearchResponse>() {
    // Set up a handler for the response
    @Override public void onSuccess(UserSearchResponse response) {
        for (BeamUser user : response) {
            System.out.println(user.username);
        }
    }

    @Override public void onFailure(Throwable throwable) {
        // ...
    }
});
```
