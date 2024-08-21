package com.services.repo.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class RepoDetails {

    @NotEmpty(message = "Name must not be empty")
    private String fullName;

    private String description;

    private String cloneUrl;

    private Integer stars;

    private Date createdAt;
}
