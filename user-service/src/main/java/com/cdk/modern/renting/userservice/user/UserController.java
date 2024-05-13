package com.cdk.modern.renting.userservice.user;

import com.cdk.modern.renting.userservice.user.request.UserCreateRequest;
import com.cdk.modern.renting.userservice.user.request.UserLoginRequest;
import com.cdk.modern.renting.userservice.user.request.UserUpdate;
import com.cdk.modern.renting.userservice.user.request.UserUpdateRequest;
import com.cdk.modern.renting.userservice.user.response.TokenResponse;
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  @PostMapping("/login")
  public TokenResponse loginUser(UserLoginRequest userLoginRequest) {
    return new TokenResponse();
  }

  @GetMapping("/refresh/{token}")
  public TokenResponse loginUser(@PathVariable String token) {
    return userService.refresh(token);
  }

  @PostMapping
  public UserInfoResponse createUser(UserCreateRequest userCreateRequest) {
    return new UserInfoResponse();
  }

  @PutMapping
  @PreAuthorize("hasAnyAuthority('WRITE')")
  public UserInfoResponse updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    authentication.getName();
    UserUpdate userUpdate = new UserUpdate();
    BeanUtils.copyProperties(userUpdateRequest, userUpdate);
    userUpdate.setUsername(authentication.getName());
    return userService.updateUser(userUpdate);
  }

}
