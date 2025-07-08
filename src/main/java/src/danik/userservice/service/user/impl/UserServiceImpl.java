package src.danik.userservice.service.user.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.entity.User;
import src.danik.userservice.mapper.user.UserMapper;
import src.danik.userservice.repository.UserRepository;
import src.danik.userservice.service.cache.CacheService;
import src.danik.userservice.service.user.UserService;
import src.danik.userservice.service.user.validator.UserValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CacheService cacheService;
    private final UserMapper userMapper;
    private final UserValidator userValidator;

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }
    public UserDto getUserByIdInDB(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User was not found"));
        return userMapper.toDto(user);
    }

    @Override
    public UserDto getUserById(Long id) {
        UserDto userDto = cacheService.getFromCache("popularUsers", id, UserDto.class);
        if (userDto != null) {
            log.info("UserDto was found in hot cache: {}", userDto);
            return userDto;
        }
        userDto = cacheService.getFromCache("users", id, UserDto.class);
        if (userDto != null) {
            log.info("UserDto was found in cold cache: {}", userDto);
            return userDto;
        }
        log.info("Fetching user with id {} from database", id);

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User was not found"));
        userDto = userMapper.toDto(user);
        cacheService.putInCache("users", id, userDto);

        return userDto;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserRegistrationDto userRegistrationDto) {
        log.info("Before Mapping: {}", userRegistrationDto);

        userValidator.isUserValid(userRegistrationDto);

        User user = userMapper.toEntity(userRegistrationDto);
        log.info("Saving user: {}", user);
        userRepository.save(user);
        UserDto userDto = userMapper.toDto(user);
        log.info("Creating userDto: {}", userDto);
        return userDto;
    }

    @Override
    @CacheEvict(value = {"users", "popularUsers"}, key = "#id")
    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getAllUsersByIds(@NotNull List<Long> ids) {
        return userRepository.findAllById(ids).stream().map(userMapper::toDto).toList();
    }

}
