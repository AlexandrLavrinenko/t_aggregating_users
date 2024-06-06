package ua.comparus.database.repository;

import ua.comparus.database.entity.User;
import ua.comparus.dto.filter.UserFilter;
import ua.comparus.dto.filter.UserFilterRecord;

import java.util.List;

public interface UserFilterRepository {

    List<User> findAllByFilter(UserFilterRecord filter);
}
