package ua.comparus.http.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.comparus.database.entity.User;
import ua.comparus.database.repository.UserRepository;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class UserRestController {

    private final UserRepository userRepository;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.getAllUsersFromAllSource());
    }

    @GetMapping("/datasource/{dataSourceName}")
    public ResponseEntity<List<User>> getAllUsersByDataSourceName(@PathVariable String dataSourceName) {
        return ResponseEntity.ok(userRepository.getAllUsersFromAllSource());
    }
}
