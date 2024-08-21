package com.services.repo.controller;

import com.services.repo.model.RepoDetails;
import com.services.repo.service.RepoService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/repositories")
public class ApiController {

    @Autowired
    private RepoService repoService;

    @GetMapping(value = "/{owner}/{repository-name}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RepoDetails> getRepo(@PathVariable("owner") String owner, @PathVariable("repository-name") String repoName) {

        log.info("GET request from {} for repo {}", owner, repoName);
        return ResponseEntity.ok(repoService.getRepo(owner, repoName));
    }

    @PostMapping(value = "/{owner}",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RepoDetails> saveRepo(@PathVariable("owner") String owner, @Valid @RequestBody RepoDetails repoDetails) {

        log.info("POST request from {} for repo creation {}", owner, repoDetails);
        return ResponseEntity.ok(repoService.saveRepo(owner, repoDetails));
    }
}
