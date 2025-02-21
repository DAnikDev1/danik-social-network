package src.danik.userservice.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    User toEntity(UserDto userDto);
}