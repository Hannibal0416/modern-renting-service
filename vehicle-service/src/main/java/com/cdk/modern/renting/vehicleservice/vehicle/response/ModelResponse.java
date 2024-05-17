package com.cdk.modern.renting.vehicleservice.vehicle.response;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import java.time.LocalDateTime;
import lombok.Data;


@Data
public class ModelResponse {

  private Integer id;
  private Integer typeId;
  private TypeResponse type;
  private Integer brandId;
  private BrandResponse brand;
  private String imageUri;
  private String name;
  private boolean active;
  protected LocalDateTime createdAt;
}
