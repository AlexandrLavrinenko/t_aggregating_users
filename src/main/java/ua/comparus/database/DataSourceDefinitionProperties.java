package ua.comparus.database;

import jakarta.annotation.PostConstruct;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ua.comparus.exception.DataSourceDefinitionException;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Validated
@ConfigurationProperties(prefix = "spring.datasource.db-definition")
public class DataSourceDefinitionProperties {

    @NotNull
    @Size(min = 1, message = "You should connect at least one data source in the application.yaml file")
    private final List<DataSourceDefinition> dataSourceDefinitions;

    @PostConstruct
    private void assertThatDatabaseNamesAreDistinct() throws DataSourceDefinitionException {
        var groupNames = dataSourceDefinitions.stream()
                .collect(Collectors.groupingBy(DataSourceDefinition::name));

        var countNotUniq = groupNames.values()
                .stream()
                .map(List::size)
                .filter(cnt -> cnt > 1)
                .count();

        if (countNotUniq > 0) {
            throw new DataSourceDefinitionException("DB name should be uniq");
        }
    }

    public record DataSourceDefinition(@NotBlank String name,
                                       @NotBlank String url,
                                       @NotBlank String table,
                                       @NotBlank String user,
                                       @NotBlank String password) {

    }

}
