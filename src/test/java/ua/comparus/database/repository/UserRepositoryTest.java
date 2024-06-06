package ua.comparus.database.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.comparus.database.entity.User;

import java.util.UUID;

@RequiredArgsConstructor
class UserRepositoryTest {

    private final UserRepository userRepository;

//    @Test
//    void getAllUsersByDataSourceName() {
//
//    }

    @Test
    void findOne() {
        User newUser = User.builder()
                .id(new UUID(1L, 10L))
                .username("test_username")
                .name("test_name")
                .surname("test_surname")
                .build();

        userRepository.saveAndFlush(newUser);

        var user = userRepository.findOne(newUser.getId()).orElseThrow();
        Assertions.assertEquals(newUser,user);
    }
}