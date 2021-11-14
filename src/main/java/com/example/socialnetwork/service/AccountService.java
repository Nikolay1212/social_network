package com.example.socialnetwork.service;

import com.example.socialnetwork.dto.AccountDto;
import com.example.socialnetwork.exception.AccountAlreadyExistException;
import com.example.socialnetwork.exception.AccountNotFoundException;
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

    public Account banAccount(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setState(Account.State.BANNED);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account unbanAccount(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setState(Account.State.CONFIRMED);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account assignModerator(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setRole(Account.Role.MODERATOR);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account removeModerator(Long id) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(id);
        if (accountOptional.isEmpty()) {
            throw new AccountNotFoundException("Account id = " + id + " not found");
        }
        Account currentAccount = accountOptional.get();
        currentAccount.setRole(Account.Role.USER);
        accountRepo.save(currentAccount);
        return currentAccount;
    }

    public Account registration(Account account) throws AccountAlreadyExistException {
        if (accountRepo.findByFirstName(account.getFirstName()) != null) {
            throw new AccountAlreadyExistException("Name " + account.getFirstName() + " already exists!");
        }
        List<Room> defaultRoomList = roomRepo.findByAccessLevel();
        account.setRooms(defaultRoomList);
        return accountRepo.save(account);
    }

    public AccountDto getOne(Long id) throws AccountNotFoundException {
        Optional<Account> accountEntityOptional = accountRepo.findById(id);
        if (accountEntityOptional.isEmpty()) {
            throw new AccountNotFoundException("User not found");
        }
        return AccountDto.from(accountEntityOptional.get());
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

    public void addNewMessage(Message message, Long id) throws AccountNotFoundException {
        Account account = accountRepo.findById(id).get();
        List<Message> messageList = account.getMessageList();
        messageList.add(message);
        account.setMessageList(messageList);
        accountRepo.save(account);
    }
}
