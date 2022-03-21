package com.egu.boot.BoardGame.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.egu.boot.BoardGame.model.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

	List<BankAccount> findAllBybranchId(int branchId);

}
