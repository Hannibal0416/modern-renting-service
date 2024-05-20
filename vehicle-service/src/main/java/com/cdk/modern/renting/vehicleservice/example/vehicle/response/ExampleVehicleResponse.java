package com.cdk.modern.renting.vehicleservice.example.vehicle.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ExampleVehicleResponse {

  protected UUID id;
  private ExampleModelResponse model;
  private String imageUri;
  private String rentPrice;
  private String name;
  private String color;
  private Short productionYear;
  private Short seatCount;
  private String transmission;
  private String fuelType;
  private boolean active;
  protected LocalDateTime createdAt;
}
