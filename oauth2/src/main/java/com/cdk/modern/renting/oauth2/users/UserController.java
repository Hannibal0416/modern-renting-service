package com.cdk.modern.renting.oauth2.users;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.IntrospectionUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ObservationAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.authorization.web.authentication.OAuth2TokenIntrospectionAuthenticationConverter;
import org.springframework.security.web.authentication.AuthenticationConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  @GetMapping
  public void getUser() {

//    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//    AuthenticationManager authenticationManager = new ObservationAuthenticationManager(authentication);
//
//    AuthenticationConverter authenticationConverter = new OAuth2TokenIntrospectionAuthenticationConverter()
//    Authentication tokenIntrospectionAuthentication = this.authenticationConverter.convert(request);
//    Authentication tokenIntrospectionAuthenticationResult =
//        this.authenticationManager.authenticate(tokenIntrospectionAuthentication);
//    log.info("a{}",tokenIntrospectionAuthenticationResult);
//
//    log.info("a{}",authentication);
  }
}
