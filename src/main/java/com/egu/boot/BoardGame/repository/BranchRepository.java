package com.egu.boot.BoardGame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.Branch;
import com.egu.boot.BoardGame.model.Theme;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

	Optional<Branch> findAllBybranchName(String branchName);

}
