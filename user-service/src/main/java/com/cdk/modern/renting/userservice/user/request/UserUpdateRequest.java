package com.cdk.modern.renting.userservice.user.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
  private String password;
  private String email;
  private String phone;

}