import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import ua.comparus.database.entity.User;
import ua.comparus.database.repository.UserRepository;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
@EnableTransactionManagement
@RequiredArgsConstructor
@TestPropertySource({"classpath:application.yaml"})
public class JpaMultipleDBIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional("db1TransactionManager")
    public void whenCreatingUser_thenCreated() {
        UUID uuid = new UUID(1L, 1000L);
        User user = User.builder()
                .id(uuid)
                .name("John")
                .surname("john@test.com")
                .username("john@test.com")
                .build();

        user = userRepository.save(user);

        assertNotNull(userRepository.findOne(user.getId()));
    }
}
