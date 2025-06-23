package src.danik.userservice.service.user.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.entity.User;
import src.danik.userservice.exception.DataValidationException;
import src.danik.userservice.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserValidator {
    private final UserRepository userRepository;

    public void isUserExistsWithUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            throw new DataValidationException(String.format("User with username %s already exists", username));
        }
    }
    public void isUserExistsWithEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            throw new DataValidationException(String.format("User with email %s already exists", email));
        }
    }
    public void isUserValid(UserRegistrationDto user) {
        isUserExistsWithUsername(user.getUsername());
        isUserExistsWithEmail(user.getEmail());
    }
}
