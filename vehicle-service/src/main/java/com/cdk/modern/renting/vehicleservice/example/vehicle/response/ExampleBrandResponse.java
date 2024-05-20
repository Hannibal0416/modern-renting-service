package com.cdk.modern.renting.vehicleservice.example.vehicle.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ExampleBrandResponse {
  private Integer id;
  private String imageUri;
  private String name;
  private boolean active;
  protected LocalDateTime createdAt;
}
