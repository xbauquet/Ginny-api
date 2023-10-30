package com.ginnyci.ginnyapi.githubauth;

public class MissingEnvironmentException extends RuntimeException {
    <T> MissingEnvironmentException(final String key, final Class<T> c) {
        super("Environment variable is rather missing or its type is no correct (" + key + ", " + c.getName() + ")");
    }
}
