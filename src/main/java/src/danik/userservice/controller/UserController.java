package src.danik.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.service.user.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Tag(name = "User API", description = "API for managing users")
@RequestMapping("/api/v1")
@Slf4j
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/users/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @Operation(summary = "Register new user")
    @PostMapping("/users")
    public ResponseEntity<UserDto> registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        log.info("Controller got UserRegistrationDto: {}", userRegistrationDto);
        return ResponseEntity.ok(userService.registerUser(userRegistrationDto));
    }

    @Operation(summary = "Delete user by id")
    @DeleteMapping(value = "/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Long id) {
        log.info("Deleting user with id {}", id);
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Get users by ids")
    @PostMapping("/users/list")
    public List<UserDto> getAllUsersByIds(@RequestBody List<Long> ids) {
        log.info("Received List of ids: {}", ids);
        return userService.getAllUsersByIds(ids);
    }




}
