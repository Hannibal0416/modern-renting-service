package com.cdk.modern.renting.userservice.user.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateRequest {
  @NotBlank(message = "Name cannot be empty")
  @Schema(name = "username", requiredMode = RequiredMode.REQUIRED)
  private String username;

  @Size(min = 8, max = 15, message
          = "Password must be between 8 and 15 characters")
  @Schema(name = "password", example = "RevExPWD", requiredMode = RequiredMode.REQUIRED)
  private String password;

  @Email(message = "Email should be valid")
  @Schema(name = "email", example = "revex@chc.com", requiredMode = RequiredMode.REQUIRED)
  private String email;

  @Schema(name = "phone", example = "123456789", requiredMode = RequiredMode.REQUIRED)
  private String phone;

}
