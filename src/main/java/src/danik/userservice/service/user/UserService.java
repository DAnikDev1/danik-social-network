package src.danik.userservice.service.user;

import jakarta.validation.Valid;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserById(Long id);

    UserDto registerUser(@Valid UserRegistrationDto userRegistrationDto);

    void delete(Long id);

    List<UserDto> getAllUsersByIds(List<Long> ids);
}
