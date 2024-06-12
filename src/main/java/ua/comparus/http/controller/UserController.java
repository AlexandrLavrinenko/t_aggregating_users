package ua.comparus.http.controller;

import ua.comparus.dto.filter.UserFilter;
import ua.comparus.dto.response.UserReadResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static ua.comparus.http.controller.OpenApiResponsesExamples.*;

@Validated
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserController {

    String FIND_ALL_DESCRIPTION = "This endpoint allows to get the list of all Users";

    @Operation(summary = "Get the list of Users", responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = List.class))})},
            description = FIND_ALL_DESCRIPTION)
    @GetMapping
    List<UserReadResponse> findAll(@Parameter(hidden = true) UserFilter userFilter);
}
