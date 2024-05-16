package com.cdk.modern.renting.vehicleservice.vehicle.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import lombok.Data;

@Data
public class VehicleRequest {
  @Schema(name = "modelId", example = "RevEx", maxLength= 100, requiredMode = RequiredMode.REQUIRED)
  @Max(100)
  private String modelId;
  @Schema(name = "name", example = "RevEx", maxLength= 100, requiredMode = RequiredMode.REQUIRED)
  @Max(100)
  private String name;
}
