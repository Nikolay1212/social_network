package com.example.socialnetwork.controller;

import com.example.socialnetwork.exception.UserAlreadyExistException;
import com.example.socialnetwork.exception.UserNotFoundException;
import com.example.socialnetwork.model.Account;
import com.example.socialnetwork.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity registration(@RequestBody Account account) {
        try {
            accountService.registration(account);
            return ResponseEntity.ok("Account " + account.getFirstName() + " was successfully added");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @GetMapping
    public ResponseEntity authorization(@RequestBody Account account) {
        try {
            if (accountService.authorize(account)) {
                return ResponseEntity.ok().body("Successful authorization!");
            } else {
                return ResponseEntity.badRequest().body("Name or login is wrong");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error authorization");
        }
    }

    @GetMapping(value = "{account-id}")
    public ResponseEntity getOneUser(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.getOne(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(accountService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
