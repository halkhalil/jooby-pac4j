# Jooby Pac4j module

[![License](https://img.shields.io/badge/License-Apache%202.0-yellowgreen.svg)](https://github.com/lodrantl/jooby-pac4j/blob/master/LICENSE)


This is a snapshot of pac4j2 module for jooby. It mimicks the API of other pac4j implementations and reuses as much code as possible.

## Usage

OAuth client, access to 

```java
//configure auth and clients
use(new Auth()
        .client(config -> {
            FacebookClient client = new FacebookClient(config.getString("facebook.key"), config.getString("facebook.token"));
            return client;
        })
        .client(new DirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator()))
);

//secure with fb
use("/fb", new AuthFilter("FacebookClient"));
get("/fb", (req) -> {
    ProfileManager pm = req.require(ProfileManager.class);
    return pm.get(true).toString();
});

//secure with basic
use("/simple", new AuthFilter("DirectBasicAuthClient"));
get("/simple", (req) -> {
    ProfileManager pm = req.require(ProfileManager.class);
    return pm.get(true).toString();
});

//try basic, then facebook
use("/both", new AuthFilter("FacebookClient,DirectBasicAuthClient"));
get("/both", (req) -> {
    ProfileManager pm = req.require(ProfileManager.class);
    return pm.get(true).toString();
});
```

## TODO

* logout route

