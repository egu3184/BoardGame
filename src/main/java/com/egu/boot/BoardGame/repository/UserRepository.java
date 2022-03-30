package com.egu.boot.BoardGame.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByUserId(String userId);

}
