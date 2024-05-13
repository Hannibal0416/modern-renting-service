package com.cdk.modern.renting.userservice.user;

import com.cdk.modern.renting.userservice.config.OAuth2Properties;
import com.cdk.modern.renting.userservice.config.SecurityConfiguration;
import com.cdk.modern.renting.userservice.domain.User;
import com.cdk.modern.renting.userservice.user.request.UserCreateRequest;
import com.cdk.modern.renting.userservice.user.request.UserUpdateRequest;

import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService{

  private final UserRepository userRepository;


  public UserInfoResponse getUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    User user = userRepository.findByUsername(authentication.getName()).orElseThrow(() -> new DataRetrievalFailureException("User not found"));
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

  @Override
  public UserInfoResponse updateUser(UserUpdateRequest userUpdate) {
    return null;
  }

}
