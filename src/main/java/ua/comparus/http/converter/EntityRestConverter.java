package ua.comparus.http.converter;

import org.mapstruct.Mapper;
import ua.comparus.database.entity.User;
import ua.comparus.dto.UserDto;

import java.util.List;

@Mapper
public interface EntityRestConverter {
    List<UserDto> mapToUserDto(List<User> users);
}
