package src.danik.userservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.entity.User;
import src.danik.userservice.mapper.user.UserMapper;
import src.danik.userservice.service.user.UserService;
import src.danik.userservice.service.user.impl.UserServiceImpl;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@Tag(name = "User API", description = "API for managing users")
@RequestMapping("/api/v1")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(authorEntity -> {
            UserDto authorDto = userMapper.toDto(authorEntity);
            return new ResponseEntity<>(authorDto, HttpStatus.OK);
        }).orElse(
                new ResponseEntity<>(HttpStatus.NOT_FOUND)
        );
    }

    
}
