package com.cdk.modern.renting.vehicleservice.vehicle.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class UpdateVehicleRequest {
  @NotNull
  @Min(value = 1)
  @Max(value = 999999)
  private Integer modelId;

  private String imageUri;

  @NotNull
  @Size(min = 3, max = 255)
  private String name;

  @NotNull
  @Min(value = 0)
  @Max(value = 999999)
  private Integer rentPrice;

  private String color;
  private Short productionYear;
  private Short seatCount;
  private String transmission;
  private String fuelType;

  @NotNull private boolean active;
}
