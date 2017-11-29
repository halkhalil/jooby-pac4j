package org.jooby.pac4j;

import org.jooby.Jooby;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.http.client.direct.DirectBasicAuthClient;
import org.pac4j.http.credentials.authenticator.test.SimpleTestUsernamePasswordAuthenticator;
import org.pac4j.oauth.client.FacebookClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Pac4jApp extends Jooby {
    final Logger logger = LoggerFactory.getLogger(Pac4jApp.class);

    {

        use(new Auth()
                .client(config -> {
                    FacebookClient client = new FacebookClient(config.getString("facebook.key"), config.getString("facebook.token"));
                    client.setUrlResolver(new MyRelativeUrlResolver());
                    return client;
                })
                .client(new DirectBasicAuthClient(new SimpleTestUsernamePasswordAuthenticator()))
        );

        use("/fb", new AuthFilter("FacebookClient"));
        get("/fb", (req) -> {
            ProfileManager pm = req.require(ProfileManager.class);
            return pm.get(true).toString();
        });

        use("/simple", new AuthFilter("DirectBasicAuthClient"));
        get("/simple", (req) -> {
            ProfileManager pm = req.require(ProfileManager.class);
            return pm.get(true).toString();
        });

        use("/both", new AuthFilter("FacebookClient,DirectBasicAuthClient"));
        get("/both", (req) -> {
            ProfileManager pm = req.require(ProfileManager.class);
            return pm.get(true).toString();
        });
    }

    public static void main(final String[] args) {
        run(Pac4jApp::new, args);
    }

}
