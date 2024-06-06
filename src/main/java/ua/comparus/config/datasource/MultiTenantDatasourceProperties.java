package ua.comparus.config.datasource;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ua.comparus.exception.DataSourceNamesMustBeUniqueException;

import java.util.List;
import java.util.Set;

@Data
@Validated
@ConfigurationProperties(prefix = "db")
public class MultiTenantDatasourceProperties {

    @NotNull
    @Size(min = 1, message = "At least one data source must be initialized via properties!")
    private final List<DataSourceDefinition> dataSourceDefinitions;

    @PostConstruct
    private void assertThatDatabaseNamesAreDistinct() {
        List<String> names = dataSourceDefinitions.stream()
                .map(DataSourceDefinition::name)
                .toList();
        Set<String> uniqueNames = Set.copyOf(names);
        if (uniqueNames.size() != names.size()) {
            throw new DataSourceNamesMustBeUniqueException();
        }
    }

    public record DataSourceDefinition(@NotBlank String name,
                                       @NotBlank String strategy,
                                       @NotBlank String url,
                                       @NotBlank String table,
                                       @NotBlank String user,
                                       @NotBlank String password,
                                       @NotNull Mapping mapping) {

    }

    public record Mapping(@NotBlank String id,
                          @NotBlank String username,
                          @NotBlank String name,
                          @NotBlank String surname) {

    }
}
