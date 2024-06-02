package org.example.backend;

public class PostNotSavedException extends RuntimeException {
    public PostNotSavedException(String message) {
        super(message);
    }
}
