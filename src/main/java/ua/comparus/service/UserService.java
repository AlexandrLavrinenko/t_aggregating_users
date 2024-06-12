package ua.comparus.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.comparus.dto.UserReadDto;
import ua.comparus.dto.filter.UserFilter;

import java.util.List;

@Validated
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public interface UserService {

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
    @GetMapping
    Page<UserReadDto> findAll(@Parameter(hidden = true) UserFilter filter,
                              @Parameter(hidden = true) Pageable pageable);

}
