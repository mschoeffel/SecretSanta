package de.mschoeffel.secretsanta.error;

public class GroupNameValidationException extends RuntimeException{

    public GroupNameValidationException(String message) {
        super(message);
    }
}
