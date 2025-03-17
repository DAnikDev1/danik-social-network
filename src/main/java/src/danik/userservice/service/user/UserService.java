package src.danik.userservice.service.user;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto registerUser(@Valid UserRegistrationDto userRegistrationDto);


    void delete(Long id);

    List<UserDto> getAllUsersByIds(List<Long> ids);
}
