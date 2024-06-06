package ua.comparus.database.repository;

import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import ua.comparus.database.entity.QUser;
import ua.comparus.database.entity.User;
import ua.comparus.database.querydsl.QPredicates;
import ua.comparus.dto.filter.UserFilterRecord;

import java.util.List;

import static ua.comparus.database.entity.QUser.*;

@RequiredArgsConstructor
public class UserFilterRepositoryImpl implements UserFilterRepository{

    private final EntityManager entityManager;
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedJdbcTemplate;

    @Override
    public List<User> findAllByFilter(UserFilterRecord filter) {
        // Querydsl
        var predicate = QPredicates.builder()
                .add(filter.username(), user.username::containsIgnoreCase)
                .add(filter.name(), user.name::containsIgnoreCase)
                .add(filter.surname(), user.surname::containsIgnoreCase)
                .build();

        return new JPAQuery<User>(entityManager)
                .select(user)
                .from(user)
                .where(predicate)
                .fetch();
    }
}
