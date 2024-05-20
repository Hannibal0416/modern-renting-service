package com.cdk.modern.renting.userservice.user;

import com.cdk.modern.renting.userservice.domain.User;
import com.cdk.modern.renting.userservice.user.request.UserCreateRequest;
import com.cdk.modern.renting.userservice.user.request.UserUpdateRequest;

import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;

import com.cdk.modern.renting.userservice.util.PasswordUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserInfoResponse getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user =
        userRepository
            .findByUsername(authentication.getName())
            .orElseThrow(() -> new DataRetrievalFailureException("User not found"));
    return toCanonical(user);
  }

  private UserInfoResponse toCanonical(User user) {
    UserInfoResponse userInfoResponse = new UserInfoResponse();
    userInfoResponse.setEmail(user.getEmail());
    userInfoResponse.setUsername(user.getUsername());
    userInfoResponse.setPhone(user.getPhone());
    return userInfoResponse;
  }

  @Override
  public UserInfoResponse createUser(UserCreateRequest userCreateRequest) {
    return null;
  }

  @Transactional
  @Override
  public UserInfoResponse updateUser(UserUpdateRequest userUpdate) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User updatedUser =
        userRepository
            .findByUsername(authentication.getName())
            .map(
                user -> {
                  user.setEmail(userUpdate.getEmail());
                  user.setPhone(userUpdate.getPhone());

                  if (StringUtils.isNotBlank(userUpdate.getPassword())) {
                    user.setPassword(PasswordUtils.prependNoop(userUpdate.getPassword()));
                  }
                  return userRepository.save(user);
                })
            .orElseThrow(() -> new DataRetrievalFailureException("User not found"));

    return toCanonical(updatedUser);
  }
}
