package ua.comparus.http.converter;

import org.mapstruct.Mapper;
import ua.comparus.database.entity.User;
import ua.comparus.dto.UserReadDto;

import java.util.List;

@Mapper
public interface EntityRestConverter {
    List<UserReadDto> mapToUserDto(List<User> users);
}
