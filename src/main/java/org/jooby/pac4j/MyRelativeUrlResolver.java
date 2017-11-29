package org.jooby.pac4j;

import org.pac4j.core.context.ContextHelper;
import org.pac4j.core.context.HttpConstants;
import org.pac4j.core.context.WebContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyRelativeUrlResolver extends org.pac4j.core.http.RelativeUrlResolver {
    final Logger logger = LoggerFactory.getLogger(Pac4jApp.class);

    @Override
    public String compute(String url, WebContext context) {
        logger.info("HHHHHHHHHH" + url);
        logger.info(context.getServerName());
        if (context != null && url != null) {
            final StringBuilder sb = new StringBuilder();

            sb.append(context.getScheme()).append("://").append(context.getServerName());

            final boolean notDefaultHttpPort = ContextHelper.isHttp(context) && context.getServerPort() != HttpConstants.DEFAULT_HTTP_PORT;
            final boolean notDefaultHttpsPort = ContextHelper.isHttps(context) && context.getServerPort() != HttpConstants.DEFAULT_HTTPS_PORT;
            if (notDefaultHttpPort || notDefaultHttpsPort) {
                sb.append(":").append(context.getServerPort());
            }

            sb.append(url.startsWith("/") ? url : "/" + url);
            logger.info(sb.toString());
            return sb.toString();
        }
        return url;
    }
}
