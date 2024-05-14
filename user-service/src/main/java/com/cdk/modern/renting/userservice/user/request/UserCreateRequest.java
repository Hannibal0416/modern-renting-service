package com.cdk.modern.renting.userservice.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class UserCreateRequest {
  @Schema(name = "username", example = "RevEx", requiredMode = RequiredMode.REQUIRED)
  private String username;
  @Schema(name = "password", example = "RevExPWD", requiredMode = RequiredMode.REQUIRED)
  private String password;
  @Schema(name = "email", example = "revex@chc.com", requiredMode = RequiredMode.REQUIRED)
  private String email;
  @Schema(name = "phone", example = "123456789", requiredMode = RequiredMode.REQUIRED)
  private String phone;

}
