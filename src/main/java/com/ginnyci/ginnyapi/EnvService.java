package com.ginnyci.ginnyapi;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EnvService {

    public final String APP_CLIENT_ID;
    public final String APP_CLIENT_SECRET;
    public final String COMPANION_CLIENT_ID;
    public final String COMPANION_CLIENT_SECRET;

    private final Environment env;

    public EnvService(final Environment env) {
        this.env = env;

        this.APP_CLIENT_ID = getString("app.github.clientId");
        this.APP_CLIENT_SECRET = getString("app.github.clientSecret");
        this.COMPANION_CLIENT_ID = getString("app.github.companion.clientId");
        this.COMPANION_CLIENT_SECRET = getString("app.github.companion.clientSecret");
    }

    private String getString(final String envKey) {
        final var value = env.getProperty(envKey, String.class);
        if (value == null) {
            throw new MissingEnvironmentException(envKey, String.class);
        }
        return value;
    }
}
