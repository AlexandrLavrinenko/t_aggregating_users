package ua.comparus.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.comparus.config.datasource.mapper.UserReadMapper;
import ua.comparus.database.entity.User;
import ua.comparus.database.querydsl.QPredicates;
import ua.comparus.database.repository.UserRepository;
import ua.comparus.dto.UserReadDto;
import ua.comparus.dto.filter.UserFilter;

import static ua.comparus.database.entity.QUser.user;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;

    @Override
    public Page<UserReadDto> findAll(UserFilter filter, Pageable pageable) {
        var predicate = QPredicates.builder()
                .add(filter.username(), user.username::containsIgnoreCase)
                .add(filter.name(), user.name::containsIgnoreCase)
                .add(filter.surname(), user.surname::containsIgnoreCase)
                .build();

        Page<User> allUserPage = userRepository.findAll(predicate, pageable);
        Page<UserReadDto> allUserDtoPage = allUserPage.map(userReadMapper::map);
        return allUserDtoPage;
//        return userRepository.findAll(predicate, pageable)
//                .map(userReadMapper::map);
    }
}
