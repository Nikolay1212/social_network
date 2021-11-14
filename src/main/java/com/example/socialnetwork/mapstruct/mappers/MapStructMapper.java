package com.example.socialnetwork.mapstruct.mappers;

import com.example.socialnetwork.dto.AccountDto;
import com.example.socialnetwork.dto.MessageDto;
import com.example.socialnetwork.dto.RoomDto;
import com.example.socialnetwork.model.Account;
import com.example.socialnetwork.model.Message;
import com.example.socialnetwork.model.Room;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {

    AccountDto accountToAccountDto(Account account);
    MessageDto messageToMessageDto(Message message);
    RoomDto roomToRoomDto(Room room);
}
