package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account register(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()
                || account.getPassword() == null || account.getPassword().length() <= 4) {
            return null;
        }
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return null;
        }
        return accountRepository.save(account);
    }

    public Account login(String username, String password) {
        Account found = accountRepository.findByUsername(username);
        return (found != null && found.getPassword().equals(password)) ? found : null;
    }
}