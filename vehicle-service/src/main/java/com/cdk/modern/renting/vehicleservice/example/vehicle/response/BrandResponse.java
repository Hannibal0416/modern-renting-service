package com.cdk.modern.renting.vehicleservice.example.vehicle.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class BrandResponse {
  private Integer id;
  private String imageUri;
  private String name;
  private boolean active;
  protected LocalDateTime createdAt;
}
