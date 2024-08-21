package com.services.repo.exception;

public class InvalidOwnerException extends RuntimeException {

    public InvalidOwnerException() {

        super(ErrorMessages.INVALID_OWNER);
    }
}
