package ua.comparus.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ua.comparus.database.entity.User;

import java.util.UUID;

public interface UserRepository extends
        JpaRepository<User, UUID>,
        UserFilterRepository,
        QuerydslPredicateExecutor<User> {
}
