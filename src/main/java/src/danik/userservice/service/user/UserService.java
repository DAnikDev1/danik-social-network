package src.danik.userservice.service.user;

import src.danik.userservice.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(Long id);
}
