package com.cdk.modern.renting.vehicleservice.example.vehicle.response;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;

@Data
public class VehicleResponse {

  protected UUID id;
  private ModelResponse model;
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
