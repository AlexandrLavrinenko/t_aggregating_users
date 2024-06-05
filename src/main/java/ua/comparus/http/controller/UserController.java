package ua.comparus.http.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ua.comparus.dto.UserDto;
import ua.comparus.http.converter.EntityRestConverter;
import ua.comparus.service.UserService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    // TODO: Check it!
    @Qualifier("entityRestMapper")
    private final EntityRestConverter converter;

    public ResponseEntity<List<UserDto>> getUsers() {
        log.debug("Called UserController#getUsers");

        val users = userService.getAllUsers();

        log.info("Received users: {}", users);
        return ResponseEntity.ok(converter.mapToUserDto(users));
    }
}
