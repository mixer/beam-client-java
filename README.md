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
// Construct the Beam API. There are no parameters.
BeamAPI beam = new BeamAPI();

// Call the #get method on a relative path, given a repsonse class.  Add a
// callback to catch the response.

Futures.addCallback(beam.get("channels", ShowChannelsResponse.class), new
FutureCallback<ShowChannelsResponse>() {
    // Define a method to be called on-success.
    @Override public void onSuccess(ShowChannelsResponse response) {
        // Here, we can handle the response that the callback provides.
        for (BeamChannel channel : response) {
            System.out.println(channel.id);
        }
    }

    // Define a method to be called on-failure.
    @Override public void onFailure(Throwable throwable) {
        throwable.printStackTrace();
    }
});
```
