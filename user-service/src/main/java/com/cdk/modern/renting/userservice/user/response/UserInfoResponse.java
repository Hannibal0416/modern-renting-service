package com.cdk.modern.renting.userservice.user.response;

import lombok.Data;

@Data
public class UserInfoResponse {
  private String username;
  private String email;
  private String phone;
}
