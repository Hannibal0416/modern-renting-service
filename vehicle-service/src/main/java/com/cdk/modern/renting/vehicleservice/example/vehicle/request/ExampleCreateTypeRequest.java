package com.cdk.modern.renting.vehicleservice.example.vehicle.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class ExampleCreateTypeRequest {
  private String imageUri;
  private String name;
  private boolean active;
}
