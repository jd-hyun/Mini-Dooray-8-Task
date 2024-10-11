package com.nhnacademy.minidooray8task.repository;

import com.nhnacademy.minidooray8task.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

}
