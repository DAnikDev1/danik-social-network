package src.danik.userservice.mapper.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import src.danik.userservice.dto.user.UserDto;
import src.danik.userservice.dto.user.UserRegistrationDto;
import src.danik.userservice.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);

    // Без данных аннотаций при тесте будет маппинг в null
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "banned", constant = "false")
    User toEntity(UserRegistrationDto userRegistrationDto);
}