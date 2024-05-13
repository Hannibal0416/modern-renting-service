package com.cdk.modern.renting.userservice.user.request;

import lombok.Data;

@Data
public class UserCreateRequest {
  private String username;
  private String password;
  private String email;
  private String phone;

}
