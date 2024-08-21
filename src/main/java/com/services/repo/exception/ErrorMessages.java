package com.services.repo.exception;

public interface ErrorMessages {

    String INVALID_OWNER = "Owner is invalid";

    String INVALID_REPOSITORY_NAME = "Repository name is invalid";

    String REPOSITORY_NOT_FOUND = "No repository found with name %s owned by %s";
}
