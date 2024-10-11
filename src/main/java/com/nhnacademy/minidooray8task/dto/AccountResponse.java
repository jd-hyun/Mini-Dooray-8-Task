package com.nhnacademy.minidooray8task.dto;

import com.nhnacademy.minidooray8task.domain.Account;
import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private String email;

    public AccountResponse(Account account) {
        this.id = account.getId();
        this.email = account.getEmail();
    }
}
