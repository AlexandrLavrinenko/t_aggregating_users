//package ua.comparus.service;
//
//import lombok.RequiredArgsConstructor;
//import lombok.val;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//    import ua.comparus.config.property.DataSourceProperties;
//import ua.comparus.database.entity.User;
//import ua.comparus.database.repository.UserRepository;
//
//import java.util.List;
//import java.util.concurrent.CompletableFuture;
//
//@Service
//@RequiredArgsConstructor
//@Transactional(readOnly = true)
//public class UserService {
//
//    private final DataSourceProperties dataSourceProperties;
//    private final UserRepository userRepository;
//
//    public List<User> getAllUsers() {
//        val futures =
//                dataSourceProperties.getDataSources().stream()
//                        .map(dataSourceInfo -> CompletableFuture.supplyAsync(() ->
//                                userRepository.getAllUsersByDataSourceName(dataSourceInfo)))
//                        .toList();
//
//        return futures.stream()
//                .map(CompletableFuture::join)
//                .flatMap(List::stream)
//                .toList();
//    }
//}
