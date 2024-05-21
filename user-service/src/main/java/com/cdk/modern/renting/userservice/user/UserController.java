package com.cdk.modern.renting.userservice.user;

import com.cdk.modern.renting.userservice.advice.ErrorResponse;
import com.cdk.modern.renting.userservice.user.request.UserCreateRequest;
import com.cdk.modern.renting.userservice.user.request.UserUpdateRequest;
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @Operation(
      summary = "Get a user by name(the name will be retrieved from the token",
      description = "Returns the user's information")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The user was not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping(produces = "application/json")
  public UserInfoResponse getUser() {
    return userService.getUser();
  }

  @Operation(summary = "create a user", description = "Returns the user's information")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Successfully created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request - bad request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public UserInfoResponse createUser(UserCreateRequest userCreateRequest) {
    return userService.createUser(userCreateRequest);
  }

  @Operation(summary = "update the user", description = "Returns the user's information")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully updated"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad Request - bad request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The user was not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @PutMapping
  public UserInfoResponse updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
    return userService.updateUser(userUpdateRequest);
  }
}
