package com.services.repo.service;

import com.services.repo.entity.Repo;
import com.services.repo.entity.RepoId;
import com.services.repo.exception.InvalidOwnerException;
import com.services.repo.exception.InvalidRepositoryNameException;
import com.services.repo.exception.RepoNotFoundException;
import com.services.repo.model.RepoDetails;
import com.services.repo.repository.RepoRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class RepoService {

    @Autowired
    private RepoRepository repository;

    public RepoDetails getRepo(String owner, String repoName) {

        if (StringUtils.isEmpty(owner)) {
            throw new InvalidOwnerException();
        }

        if (StringUtils.isEmpty(repoName)) {
            throw new InvalidRepositoryNameException();
        }

        Optional<Repo> repoOptional = repository.findById(new RepoId(owner, repoName));

        Repo repo = repoOptional.orElseThrow(() -> new RepoNotFoundException(owner, repoName));

        RepoDetails repoDetails = RepoDetails.builder()
                .fullName(repo.getRepoId().getRepoName())
                .description(repo.getDescription())
                .cloneUrl(repo.getCloneUrl())
                .stars(repo.getStars())
                .createdAt(repo.getCreatedAt())
                .build();

        log.info("Retrieved repo : {}", repoDetails);
        return repoDetails;
    }

    public RepoDetails saveRepo(String owner, RepoDetails repoDetails) {

        if (StringUtils.isEmpty(owner)) {
            throw new InvalidOwnerException();
        }

        Repo repo = Repo.builder()
                .repoId(new RepoId(owner, repoDetails.getFullName()))
                .description(repoDetails.getDescription())
                .cloneUrl(repoDetails.getCloneUrl())
                .stars(repoDetails.getStars())
                .createdAt(new Date())
                .build();

        repo = repository.save(repo);

        repoDetails.setCreatedAt(repo.getCreatedAt());

        log.info("Saved repo : {}", repoDetails);
        return repoDetails;
    }
}
