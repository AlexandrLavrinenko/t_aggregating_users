package ua.comparus.service;

import com.querydsl.core.types.Predicate;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ua.comparus.config.datasource.mapper.UserReadMapper;
import ua.comparus.database.entity.User;
import ua.comparus.database.repository.UserRepository;
import ua.comparus.dto.UserReadDto;
import ua.comparus.dto.filter.UserFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {

    @MockBean
    private UserReadMapper userReadMapper;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Test
    void testFindAll() {
        // GIVEN
        when(userRepository.findAll(Mockito.<Predicate>any(), Mockito.<Pageable>any()))
                .thenReturn(new PageImpl<User>(new ArrayList<User>()));
        UserFilter filter = new UserFilter("janedoe", "Name", "Doe");

        QPageRequest pageable = QPageRequest.of(10, 3);

        // WHEN
        Page<UserReadDto> actualFindAllResult = userServiceImpl.findAll(filter, pageable);

        // THEN
        verify(userRepository).findAll(isA(Predicate.class), isA(Pageable.class));
        assertTrue(pageable.getSort().toList().isEmpty());
        assertTrue(actualFindAllResult.toList().isEmpty());
        verify(userRepository, times(1)).findAll(any(Predicate.class), any(Pageable.class));
    }
}