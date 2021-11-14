package com.example.socialnetwork.controller;

import com.example.socialnetwork.exception.AccountAlreadyExistException;
import com.example.socialnetwork.exception.AccountNotFoundException;
import com.example.socialnetwork.mapstruct.mappers.MapStructMapper;
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

    @Autowired
    private MapStructMapper mapStructMapper;

    @GetMapping(value = "/ban/{account-id}")
    public ResponseEntity banAccount(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.banAccount(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/unban/{account-id}")
    public ResponseEntity unbanAccount(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.unbanAccount(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/moderator/assign/{account-id}")
    public ResponseEntity assignModerator(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.assignModerator(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/moderator/remove/{account-id}")
    public ResponseEntity removeModerator(@PathVariable("account-id") Long id) {
        try {
            return ResponseEntity.ok(accountService.removeModerator(id));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity registration(@RequestBody Account account) {
        try {
            accountService.registration(account);
            return ResponseEntity.ok("Account " + account.getFirstName() + " was successfully added");
        } catch (AccountAlreadyExistException e) {
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
        } catch (AccountNotFoundException e) {
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
