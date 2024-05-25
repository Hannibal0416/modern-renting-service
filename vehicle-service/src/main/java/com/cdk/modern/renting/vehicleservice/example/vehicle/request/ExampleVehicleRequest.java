package com.cdk.modern.renting.vehicleservice.example.vehicle.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExampleVehicleRequest {
  @Schema(name = "modelId", example = "RevEx", maxLength= 10, requiredMode = RequiredMode.REQUIRED)
  @Size(max=10)
  private String modelId;
  @Schema(name = "name", example = "RevEx", maxLength= 10, requiredMode = RequiredMode.REQUIRED)
  @Size(max=10)
  private String name;
}
