package ua.comparus.config.property;

import lombok.Builder;

import java.util.Map;

@Builder
public record DatabaseProperty(String name,
                               String strategy,
                               String url,
                               String table,
                               String user,
                               String password,
                               Map<String, String> mapping) {
}
