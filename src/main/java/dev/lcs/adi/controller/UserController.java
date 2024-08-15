package dev.lcs.adi.controller;

import dev.lcs.adi.controller.dto.AccountResponseDTO;
import dev.lcs.adi.controller.dto.CreateAccountDTO;
import dev.lcs.adi.controller.dto.CreateUserDto;
import dev.lcs.adi.controller.dto.UpdateUserDTO;
import dev.lcs.adi.entity.User;
import dev.lcs.adi.repository.UserRepository;
import dev.lcs.adi.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody CreateUserDto createUserDto) {
        var userId = userService.createUser(createUserDto);
        return ResponseEntity.created(URI.create("/v1/users/" + userId.toString())).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") String userId ) {
    var user = userService.getUserById(userId);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
    var users = userService.listUsers();
    return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUserById(@PathVariable("userId") String userId,
                                               @RequestBody UpdateUserDTO updateUserDto){

        userService.updateUserById(userId, updateUserDto);
        return ResponseEntity.noContent().build();

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteById(@PathVariable("userId") String userId){
    userService.deleteById(userId);
    return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/accounts")
    public ResponseEntity<Void> createUser(@PathVariable("userId") String userId,
                                           @RequestBody CreateAccountDTO createAccountDTO) {
        userService.createAccount(userId, createAccountDTO);
    return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> listAccounts(@PathVariable("userId") String userId) {


        var accounts = userService.listAccounts(userId);
        return ResponseEntity.ok(accounts);
    }


}
