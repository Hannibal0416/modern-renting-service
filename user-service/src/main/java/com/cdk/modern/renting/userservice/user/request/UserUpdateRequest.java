package com.cdk.modern.renting.userservice.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserUpdateRequest {
  private String password;

  @NotBlank(message = "Email is mandatory")
  @Email(message = "Email should be valid")
  private String email;

  // Phone number format:
  // 1. allows optional international code with prefix "+"
  // 2. main phone number should be 10 digits
  @NotBlank(message = "Phone number is mandatory")
  @Pattern(
      regexp = "^(\\+\\d{1,3}[- ]?)?\\d{10}$",
      message =
          "Phone number should be valid, the format should be either 0900123456 or +10900123456")
  private String phone;
}
