package com.cdk.modern.renting.vehicleservice.vehicle.response;

import com.cdk.modern.renting.vehicleservice.domain.Model;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;

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
