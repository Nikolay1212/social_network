package com.example.socialnetwork.service;

import com.example.socialnetwork.exception.RoomAlreadyExistException;
import com.example.socialnetwork.model.Message;
import com.example.socialnetwork.model.Room;
import com.example.socialnetwork.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepo roomRepo;

    public void send(Message message) {
        Room room = message.getRoom();
        List<Message> messageList = room.getMessageList();
        messageList.add(message);
        room.setMessageList(messageList);
        roomRepo.save(room);
    }

    public void create(Room room) throws RoomAlreadyExistException {
        if(roomRepo.findByAccessLevel().isEmpty()) {
            roomRepo.save(room);
        } else {
            throw new RoomAlreadyExistException("Room named " + room.getTitle() + " already exists");
        }
    }
}
