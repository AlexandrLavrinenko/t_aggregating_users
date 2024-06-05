//package ua.comparus.database.repository;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import lombok.val;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Repository;
//import ua.comparus.config.DataSourceContextHolder;
//import ua.comparus.config.property.DataSourceInfo;
//import ua.comparus.database.entity.User;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//@Repository
//@RequiredArgsConstructor
//public class UserRepositoryImpl implements UserRepository {
//
//    public static final String SELECT_FIELDS_FROM_TABLE_TEMPLATE = "SELECT %s FROM %s";
//    public static final String AS = " AS ";
//    public static final String COMA_AND_SPACE = ", ";
//    public static final String DB_EXCEPTION_MESSAGE_TEMPLATE = "Error during getting users from \"{}\" database";
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public List<User> getAllUsersByDataSourceName(DataSourceInfo dataSourceInfo) {
//
//        List<User> users = new ArrayList<>();
//
//        try {
//            DataSourceContextHolder.setDataSourceKey(dataSourceInfo.name());
//
//            val sql = buildSqlQuery(dataSourceInfo);
//            users = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(User.class));
//        } catch (Exception e) {
//            log.error(DB_EXCEPTION_MESSAGE_TEMPLATE, dataSourceInfo.name(), e);
//        } finally {
//            DataSourceContextHolder.clearDataSourceKey();
//        }
//        return users;
//    }
//
//    private String buildSqlQuery(DataSourceInfo dataSourceInfo) {
//        val selectFields = new StringBuilder();
//
//        for (Map.Entry<String, String> entry : dataSourceInfo.mapping().entrySet()) {
//            selectFields.append(entry.getValue()).append(AS).append(entry.getKey()).append(COMA_AND_SPACE);
//        }
//        selectFields.deleteCharAt(selectFields.length() - COMA_AND_SPACE.length());
//
//        return String.format(SELECT_FIELDS_FROM_TABLE_TEMPLATE, selectFields, dataSourceInfo.table());
//    }
//}
