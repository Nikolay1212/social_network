package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.MessageDto;
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

    public MessageDto receive(MessageDto messageDto) {
        Message message = new Message();
        message.setAccount(accountRepo.getById(messageDto.getSender_id()));
        message.setContent(messageDto.getContent());
        message.setRoom(roomRepo.getById(messageDto.getRoom_id()));
        messageRepo.save(message);
        roomRepo.save(roomRepo.getById(messageDto.getRoom_id()));
        return messageDto;
    }

    public MessageDto send(Long messageId) {
        Message message = messageRepo.getById(messageId);
        return MessageDto.from(message);
    }

    public void delete(Long messageId) {
        messageRepo.deleteById(messageId);
    }
}
