package ua.comparus.database;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class DataSourceContextHolder {

    private final ThreadLocal<DataSourceContext> dataSourceContext = new ThreadLocal<>();

    public void remove() {
        dataSourceContext.remove();
    }

    public record DataSourceContext(String name) {
    }

}
