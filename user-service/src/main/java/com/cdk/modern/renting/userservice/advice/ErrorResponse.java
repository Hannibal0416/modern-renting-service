package com.cdk.modern.renting.userservice.advice;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;
import lombok.Data;

@Data
public class ErrorResponse {
  @Schema(name = "errors", example = "[\"Not found\"]", requiredMode = RequiredMode.REQUIRED)
  private List<String> errors;
 }
