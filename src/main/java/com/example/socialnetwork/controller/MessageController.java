package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.AccountDto;
import com.example.socialnetwork.exception.UserNotFoundException;
import com.example.socialnetwork.model.Message;
import com.example.socialnetwork.service.AccountService;
import com.example.socialnetwork.service.MessageService;
import com.example.socialnetwork.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/user/sendMessage")
public class MessageController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RoomService roomService;

    @PostMapping("/{id}")
    public String sendMessage(@RequestBody Message message, @PathVariable("id") Long id) {
        try {
            AccountDto account = accountService.getOne(id);
            messageService.send(message);
            roomService.send(message);
            accountService.addNewMessage(message, account.getId());
            return message.getText();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Account not found").getBody();
        }
    }
}
