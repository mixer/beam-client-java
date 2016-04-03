# beam-java [![Build Status](https://travis-ci.org/WatchBeam/beam-client-java.svg?branch=master)](https://travis-ci.org/WatchBeam/beam-client-java) [![](https://badges.gitter.im/MCProHosting/beam.png)](https://gitter.im/MCProHosting/beam-dev)

This is the official Java client for the [beam.pro](https://beam.pro) API.  It
currently supports `v1` and is a work in progress.

## Documentation

Confused about a method's intent?  Not sure where to get a piece of information?
Our [Javadocs](https://developer.beam.pro/doc/java-client/) are the place to go!
That link contains the most up-to-date docs for this API client.

## Building

We use [Maven](http://maven.apache.org/) to build Beam's Java client.  Once you have [Maven installed](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html), there are two easy steps to getting the
client in your classpath.

First, add the [Beam repo](https://maven.beam.pro) to your `pom.xml` as a `<repository>` as follows:

```xml
<repositories>
  <repository>
    <id>beam-snapshots</id>
    <url>https://maven.beam.pro/content/repositories/snapshots/</url>
  </repository>
</repositories>
```

And secondly, add this project as a `dependency` in your `pom.xml`:

```xml
<dependencies>
  <dependency>
    <groupId>pro.beam</groupId>
    <artifactId>api</artifactId>
    <version>1.9.2-SNAPSHOT</version>
  </dependency>
</dependencies>
```

Once these steps are completed, you should have the client on your
classpath, and are set to get programming!

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
Futures.addCallback(beam.use(UsersService.class).search("tta"), new ResponseHandler<UserSearchResponse>() {
    // Set up a handler for the response
    @Override public void onSuccess(UserSearchResponse response) {
        for (BeamUser user : response) {
            System.out.println(user.username);
        }
    }
});
```
