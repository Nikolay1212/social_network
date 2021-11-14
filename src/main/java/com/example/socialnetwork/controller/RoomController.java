package com.example.socialnetwork.controller;

import com.example.socialnetwork.exception.AccountNotFoundException;
import com.example.socialnetwork.exception.RoomAlreadyExistException;
import com.example.socialnetwork.model.Room;
import com.example.socialnetwork.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping(value = "/create/default")
    public ResponseEntity createDefaultRoom(@RequestBody Room room) {
        try {
            roomService.createDefaultRoom(room);
            return ResponseEntity.ok().body("Room " + room.getTitle() + " successfully added!");
        } catch (RoomAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = "/create/private")
    public ResponseEntity createPrivateRoom(@RequestBody Room room) {
        roomService.createPrivateRoom(room);
        return ResponseEntity.ok().body("Private Room " + room.getTitle() + " successfully added!");
    }

    @GetMapping(value = "/add/{account_id}/{room_id}")
    public ResponseEntity addNewAccount(@PathVariable("account_id") Long accountId, @PathVariable("room_id") Long roomId) throws AccountNotFoundException {
        return ResponseEntity.ok(roomService.addNewAccount(roomId, accountId));
    }
}
