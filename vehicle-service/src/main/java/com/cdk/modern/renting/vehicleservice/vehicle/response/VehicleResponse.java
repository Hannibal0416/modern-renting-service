package com.cdk.modern.renting.vehicleservice.vehicle.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.Data;

@Data
public class VehicleResponse {

  @Schema(name = "modelId", example = "RevEx", maxLength= 100, requiredMode = RequiredMode.REQUIRED)
  private String modelId;
  @Schema(name = "name", example = "RevEx", maxLength= 100, requiredMode = RequiredMode.REQUIRED)
  private String name;
}
