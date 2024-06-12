package ua.comparus.config.datasource.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.comparus.database.entity.User;
import ua.comparus.dto.UserReadDto;

@Component
@RequiredArgsConstructor
public class UserReadMapper implements Mapper<User, UserReadDto> {

    @Override
    public UserReadDto map(User object) {

        return new UserReadDto(
                object.getId().toString(),
                object.getUsername(),
                object.getName(),
                object.getSurname()
        );
    }
}