package ua.comparus.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ua.comparus.config.property.DataSourceInfo;
import ua.comparus.database.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>/*, JpaSpecificationExecutor<User>*/ {

    List<User> getAllUsersByDataSourceName(DataSourceInfo dataSourceInfo);

    List<User> getAllUsersFromAllSource();
}
