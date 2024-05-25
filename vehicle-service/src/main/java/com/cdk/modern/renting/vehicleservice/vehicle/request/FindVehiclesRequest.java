package com.cdk.modern.renting.vehicleservice.vehicle.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FindVehiclesRequest {
  @Schema(name = "typeId", example = "100001", requiredMode = RequiredMode.NOT_REQUIRED)
  private Integer typeId;

  @Schema(name = "brandId", example = "100001", requiredMode = RequiredMode.NOT_REQUIRED)
  private Integer brandId;

  @Schema(name = "modelName", example = "Cayenne", maxLength = 255, requiredMode = RequiredMode.NOT_REQUIRED)
  @Size(max=255)
  private String modelName;

  @Schema(name = "modelId", example = "1", maxLength = 255, requiredMode = RequiredMode.NOT_REQUIRED)
  private Integer modelId;

  @Schema(name = "name", example = "Hannibal", maxLength = 255, requiredMode = RequiredMode.NOT_REQUIRED)
  @Size(max=255)
  private String name;
}
