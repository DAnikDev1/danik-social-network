package src.danik.userservice.service.user.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.entity.User;
import src.danik.userservice.mapper.user.UserMapper;
import src.danik.userservice.repository.UserRepository;
import src.danik.userservice.service.user.UserService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto);
    }

    @Override
    @Transactional
    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        log.info("Before Mapping: {}", userRegistrationDto);
        User user = userMapper.toEntity(userRegistrationDto);
        log.info("Saving user: {}", user);
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        log.info("Creating userDto: {}", userDto);
        return userDto;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
