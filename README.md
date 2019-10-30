# mixer-java [![Build Status](https://travis-ci.org/mixer/beam-client-java.svg?branch=master)](https://travis-ci.org/mixer/beam-client-java) [![](https://badges.gitter.im/mixer/mixer.png)](https://gitter.im/mixer/developers)

This is the official Java client for the [mixer.com](https://mixer.com) API.  It
currently supports `v1` and is a work in progress.

## Documentation

Confused about a method's intent?  Not sure where to get a piece of information?
Our [Javadocs](https://mixer.github.io/beam-client-java/) are the place to go!
That link contains the most up-to-date docs for this API client.

## Including beam-client-java in your Project

We use [Maven](http://maven.apache.org/) to build Mixer's Java client.  Once you have [Maven installed](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html), there are a few steps to getting the
client in your classpath.

Our Maven Artifacts are published to GitHub's Package Registry, so you'll need to set that up.

First in your project, add the appropriate repository section to your `pom.xml` as a `<repository>` as follows:

```xml
<repositories>
  <repository>
    <id>github</id>
    <name>GitHub Mixer Client Java Apache Maven Packages</name>
    <url>https://maven.pkg.github.com/mixer/beam-client-java</url>
  </repository>
</repositories>
```

And secondly, add this project as a `dependency` in your `pom.xml`:

```xml
<dependencies>
  <dependency>
    <groupId>com.mixer</groupId>
    <artifactId>api</artifactId>
    <version>6.0.0</version>
  </dependency>
</dependencies>
```

The last thing you'll need to do is authenticate with GitHub's Package Registry as it currently does not support Anonymous Access. You can do this by adding some settings to your `~/.m2/settings.xml` file:
- USERNAME should be your GitHub Username
- Token should be a personal access token with the `read:packages` scope.
```xml
<servers>
  <server>
    <id>github</id>
    <username>USERNAME</username>
    <password>TOKEN</password>
  </server>
</servers>
```

You can read more about this process on [Github's Help page](https://help.github.com/en/github/managing-packages-with-github-package-registry/configuring-apache-maven-for-use-with-github-package-registry#authenticating-to-github-package-registry)

Once these steps are completed, you should have the client on your
classpath, and are set to get programming!

## Example:

Here, an example of how to use the built-in Future callback system provided in
Guava follows.  In this example, we'll construct the Mixer API, and then get a
list of all channel IDs.

```java
// Construct an instance of the Mixer API such that we can query certain
// endpoints for data.
MixerAPI mixer = new MixerAPI();

// Invoke the `UsersService.class` in order to access the methods within
// that service.  Then, assign a callback using Guava's FutureCallback
// class so we can act on the response.
Futures.addCallback(mixer.use(UsersService.class).search("tta"), new ResponseHandler<UserSearchResponse>() {
    // Set up a handler for the response
    @Override public void onSuccess(UserSearchResponse response) {
        for (MixerUser user : response) {
            System.out.println(user.username);
        }
    }
});
```
## Developing

### Docs

To build and publish the docs run:
`mvn javadoc:javadoc scm-publish:publish-scm`
