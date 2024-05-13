package com.cdk.modern.renting.userservice.user;

import com.cdk.modern.renting.userservice.user.request.UserCreateRequest;
import com.cdk.modern.renting.userservice.user.request.UserUpdateRequest;
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @GetMapping
  public UserInfoResponse getUser() {
    return new UserInfoResponse();
  }

  @PostMapping
  public UserInfoResponse createUser(UserCreateRequest userCreateRequest) {
    return userService.createUser(userCreateRequest);
  }

  @PutMapping
  public UserInfoResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
    return userService.updateUser(userUpdateRequest);
  }

}
