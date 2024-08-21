package com.services.repo.repository;

import com.services.repo.entity.Repo;
import com.services.repo.entity.RepoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRepository extends JpaRepository<Repo, RepoId> {

}
