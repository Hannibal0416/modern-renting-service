package com.cdk.modern.renting.userservice.user.request;

import lombok.Data;

@Data
public class UserLoginRequest {
  private String username;
  private String password;

}
