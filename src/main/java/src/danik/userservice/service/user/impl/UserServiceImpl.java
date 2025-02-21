package src.danik.userservice.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import src.danik.userservice.entity.User;
import src.danik.userservice.mapper.user.UserMapper;
import src.danik.userservice.repository.UserRepository;
import src.danik.userservice.service.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
}
