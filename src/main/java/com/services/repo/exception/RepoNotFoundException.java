package com.services.repo.exception;

public class RepoNotFoundException extends RuntimeException {

    public RepoNotFoundException(String owner, String repo) {

        super(String.format(ErrorMessages.REPOSITORY_NOT_FOUND, repo, owner));
    }
}
