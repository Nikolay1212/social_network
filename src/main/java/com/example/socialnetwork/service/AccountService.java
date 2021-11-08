package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.AccountDto;
import com.example.socialnetwork.exception.UserAlreadyExistException;
import com.example.socialnetwork.exception.UserNotFoundException;
import com.example.socialnetwork.model.Account;
import com.example.socialnetwork.model.Message;
import com.example.socialnetwork.model.Room;
import com.example.socialnetwork.repository.AccountRepo;
import com.example.socialnetwork.repository.RoomRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private RoomRepo roomRepo;

    public Account registration(Account account) throws UserAlreadyExistException {
        if (accountRepo.findByFirstName(account.getFirstName()) != null) {
            throw new UserAlreadyExistException("Name " + account.getFirstName() + " already exists!");
        }
        List<Room> defaultRoomList = roomRepo.findByAccessLevel();
        account.setRooms(defaultRoomList);
        return accountRepo.save(account);
    }

    public AccountDto getOne(Long id) throws UserNotFoundException {
        Optional<Account> userEntityOptional = accountRepo.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return AccountDto.from(userEntityOptional.get());
    }

    public boolean authorize(Account account) {
        Account accountRepoByFirstName = accountRepo.findByFirstName(account.getFirstName());
        if (accountRepoByFirstName != null && accountRepoByFirstName.getHashPassword().equals(account.getHashPassword())) {
            accountRepoByFirstName.setRooms(roomRepo.findByAccessLevel());
            accountRepo.save(accountRepoByFirstName);
            return true;
        }
        return false;
    }

    public Long delete(Long id) {
        accountRepo.deleteById(id);
        return id;
    }

    public void addNewMessage(Message message, Long id) throws UserNotFoundException {
        Account account = accountRepo.findById(id).get();
        List<Message> messageList = account.getMessageList();
        messageList.add(message);
        account.setMessageList(messageList);
        accountRepo.save(account);
    }
}
