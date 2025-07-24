package src.danik.userservice.service.user;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;

import java.util.List;

public interface UserService {
    Page<UserDto> getAllUsers(Pageable pageable);

    UserDto getUserById(Long id);

    UserDto getUserByIdInDB(Long id);

    UserDto registerUser(@Valid UserRegistrationDto userRegistrationDto);

    void delete(Long id);

    List<UserDto> getAllUsersByIds(List<Long> ids);
}
