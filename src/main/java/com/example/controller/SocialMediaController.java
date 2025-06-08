package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()
                || account.getPassword() == null || account.getPassword().length() <= 4) {
            return ResponseEntity.badRequest().build();
        }

        Account created = accountService.register(account);
        if (created == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.ok(created);
    }

    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        Account loggedIn = accountService.login(account.getUsername(), account.getPassword());
        return loggedIn != null ? ResponseEntity.ok(loggedIn) : ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        Message created = messageService.createMessage(message);
        return created != null ? ResponseEntity.ok(created) : ResponseEntity.badRequest().build();
    }

    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{id}")
    public ResponseEntity<Message> getMessageById(@PathVariable int id) {
        Message message = messageService.getMessageById(id);
        return message != null ? ResponseEntity.ok(message) : ResponseEntity.ok().build();
    }

    @DeleteMapping("/messages/{id}")
    public ResponseEntity<?> deleteMessage(@PathVariable int id) {
        boolean deleted = messageService.deleteMessageById(id);
        if (deleted) {
            return ResponseEntity.ok(1); 
        } else {
            return ResponseEntity.ok().build();
        }
    }


    @PatchMapping("/messages/{id}")
    public ResponseEntity<Integer> updateMessage(@PathVariable int id, @RequestBody Message message) {
        String text = message.getMessageText();
        if (text == null || text.isBlank() || text.length() > 255) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        int updated = messageService.updateMessageText(id, text);
        if (updated == 1) {
            return ResponseEntity.ok(1);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/accounts/{id}/messages")
    public List<Message> getMessagesByUserId(@PathVariable int id) {
        return messageService.getMessagesByAccountId(id);
    }
}