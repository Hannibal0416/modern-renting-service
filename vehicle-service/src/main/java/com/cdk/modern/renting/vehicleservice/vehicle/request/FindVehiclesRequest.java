package com.cdk.modern.renting.vehicleservice.vehicle.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FindVehiclesRequest {
  @Schema(name = "typeId", example = "100001", requiredMode = RequiredMode.NOT_REQUIRED)
  private String typeId;

  @Schema(name = "brandId", example = "100001", requiredMode = RequiredMode.NOT_REQUIRED)
  private String brandId;

  @Schema(name = "modelName", example = "Cayenne", maxLength = 255, requiredMode = RequiredMode.NOT_REQUIRED)
  @Max(255)
  private String modelName;

  @Schema(name = "name", example = "Hannibal", maxLength = 255, requiredMode = RequiredMode.NOT_REQUIRED)
  @Max(255)
  private String name;
}
