package com.services.repo.exception;

public class InvalidRepositoryNameException extends RuntimeException {

    public InvalidRepositoryNameException() {

        super(ErrorMessages.INVALID_REPOSITORY_NAME);
    }
}
