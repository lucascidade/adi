package dev.lcs.adi.service;

import dev.lcs.adi.controller.dto.AccountResponseDTO;
import dev.lcs.adi.controller.dto.CreateAccountDTO;
import dev.lcs.adi.controller.dto.CreateUserDto;
import dev.lcs.adi.controller.dto.UpdateUserDTO;
import dev.lcs.adi.entity.Account;
import dev.lcs.adi.entity.BillingAddress;
import dev.lcs.adi.entity.User;
import dev.lcs.adi.repository.AccountRepository;
import dev.lcs.adi.repository.BillingAddressRepository;
import dev.lcs.adi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final BillingAddressRepository billingAddressRepository;

    public UserService(UserRepository userRepository, AccountRepository accountRepository, BillingAddressRepository billingAddressRepository) {
        this.userRepository = userRepository;
        this.accountRepository = accountRepository;
        this.billingAddressRepository = billingAddressRepository;
    }

    public UUID createUser(CreateUserDto createUserDto){
        // DTO - ENTITY

        var entity = new User(null,
                createUserDto.username(),
                createUserDto.email(),
                createUserDto.password(),
                null,
                null);
        var userSaved = userRepository.save(entity);

        return userSaved.getUserId();
    }

    public Optional<User> getUserById(String userId){
       return userRepository.findById(UUID.fromString(userId));
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public void updateUserById(String userId, UpdateUserDTO updateUserDTO){
        if(userRepository.existsById(UUID.fromString(userId))){
            var user = userRepository.findById(UUID.fromString(userId)).get();
            user.setUsername(updateUserDTO.username());
            user.setPassword(updateUserDTO.password());
            userRepository.save(user);
        }

    }
    public void deleteById(String userId){
        if(userRepository.existsById(UUID.fromString(userId))){
            userRepository.deleteById(UUID.fromString(userId));
        }
    }

    public void createAccount(String userId, CreateAccountDTO createAccountDTO) {
       var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND)));

    //DTO - > ENTITY
        var account = new Account(
                UUID.randomUUID(),
                user,
                null,
                createAccountDTO.description(),
                new ArrayList<>()
        );

       var accountCreated = accountRepository.save(account);

       var billingAddress = new BillingAddress(
               accountCreated.getAccountId(),
               account,
               createAccountDTO.street(),
               createAccountDTO.number()
       );

       billingAddressRepository.save(billingAddress);
    }

    public List<AccountResponseDTO> listAccounts(String userId) {

        var user = userRepository.findById(UUID.fromString(userId)).orElseThrow(() -> new ResponseStatusException((HttpStatus.NOT_FOUND)));

        return user.getAccounts()
                .stream()
                .map(ac -> new AccountResponseDTO(ac.getAccountId().toString(), ac.getDescription())).toList();

    }
}
