package com.example.socialnetwork.service;

import com.example.socialnetwork.model.Account;
import com.example.socialnetwork.model.Message;
import com.example.socialnetwork.repository.AccountRepo;
import com.example.socialnetwork.repository.MessageRepo;
import com.example.socialnetwork.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class MessageService {

    private static final Logger log = Logger.getLogger(String.valueOf(MessageService.class));

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private RoomRepo roomRepo;

    public void send(Message message) {
        messageRepo.save(message);
    }
}
