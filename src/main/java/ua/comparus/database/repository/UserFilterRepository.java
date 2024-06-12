package ua.comparus.database.repository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.comparus.database.entity.User;
import ua.comparus.dto.filter.UserFilter;

import java.util.List;

import static ua.comparus.service.open_api.OpenApiResponsesExamples.*;

@Tag(name = "User API")
@ApiResponse(
        responseCode = "405",
        description = "Method Not Allowed",
        content = @Content(
                schema = @Schema(example = METHOD_NOT_ALLOWED_EXAMPLE)))
@ApiResponse(
        responseCode = "406",
        description = "Not Acceptable",
        content = {@Content(
                mediaType = "text/plain",
                examples = @ExampleObject(value = NOT_ACCEPTABLE_EXAMPLE))})
@ApiResponse(
        responseCode = "500",
        description = "Internal Server Error ",
        content = @Content(
                schema = @Schema(example = INTERNAL_SERVER_ERROR_EXAMPLE)))
@Validated
@RequestMapping(value = "/api/v1/users/filter", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserFilterRepository {

    String FIND_ALL_USER_DESCRIPTION = "This endpoint allows to get the list of all Users";

    @Operation(
            summary = "Get the list of Users",
            responses = {@ApiResponse(
                    responseCode = "200",
                    description = "OK",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = List.class))})},
            description = FIND_ALL_USER_DESCRIPTION)
    List<User> findAllByFilter(UserFilter filter);
}
