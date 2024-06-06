package ua.comparus.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import ua.comparus.database.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static ua.comparus.config.datasource.MultiTenantDatasourceProperties.DataSourceDefinition;

public interface UserRepository extends
        JpaRepository<User, UUID>,
        UserFilterRepository,
        QuerydslPredicateExecutor<User> {

    @Query("select u from User u")
    List<User> getAllUsersByDataSourceName(DataSourceDefinition dataSourceDefinition);

    @Query("select u from User u " +
           "where u.id = :id")
    Optional<User> findOne(UUID id);
}
