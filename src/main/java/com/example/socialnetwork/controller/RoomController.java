package com.example.socialnetwork.controller;

import com.example.socialnetwork.exception.RoomAlreadyExistException;
import com.example.socialnetwork.model.Room;
import com.example.socialnetwork.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity createRoom(@RequestBody Room room) {
        try {
            roomService.create(room);
            return ResponseEntity.ok().body("Room " + room.getTitle() + " successfully added!");
        } catch (RoomAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
