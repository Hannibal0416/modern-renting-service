package com.cdk.modern.renting.userservice.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {

  @GetMapping
  public String hello() {
    return "hello";
  }

  @PutMapping
  @PreAuthorize("hasAnyAuthority('WRITE')")
  public String put(@RequestBody PutBody putBody) {
    log.info(putBody.getId());
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication.getName();
  }

}
@Data
class PutBody {
  private String id;
}