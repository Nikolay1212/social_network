package com.example.socialnetwork.controller;

import com.example.socialnetwork.dto.MessageDto;
import com.example.socialnetwork.service.AccountService;
import com.example.socialnetwork.service.MessageService;
import com.example.socialnetwork.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/send/{message_id}")
    public ResponseEntity send(@PathVariable("message_id") Long messageId) {
        return ResponseEntity.ok(messageService.send(messageId));
    }

    @GetMapping(value = "/receive")
    public ResponseEntity receive(@RequestBody MessageDto messageDto) {
        return ResponseEntity.ok(messageService.receive(messageDto));
    }

    @DeleteMapping(value = "/delete/{message_id}")
    public ResponseEntity delete(@PathVariable("message_id") Long messageId) {
        messageService.delete(messageId);
        return ResponseEntity.ok("Message deleted");
    }
}
