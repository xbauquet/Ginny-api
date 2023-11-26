package com.ginnyci.ginnyapi;

public class MissingEnvironmentException extends RuntimeException {
    public <T> MissingEnvironmentException(final String key, final Class<T> c) {
        super("Environment variable is rather missing or its type is no correct (" + key + ", " + c.getName() + ")");
    }
}
