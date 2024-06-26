package com.cdk.modern.renting.vehicleservice.advice;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
  @Schema(name = "errors", example = "[\"Not found\"]", requiredMode = RequiredMode.REQUIRED)
  private List<String> errors;
 }
