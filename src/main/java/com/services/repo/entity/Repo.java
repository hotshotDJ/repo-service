package com.services.repo.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repo {

    @EmbeddedId
    private RepoId repoId;

    private String description;

    private String cloneUrl;

    private Integer stars;

    private Date createdAt;
}
