package com.cdk.modern.renting.vehicleservice.metadata.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class CreateModelRequest {
  @NotNull
  private Integer typeId;

  @NotNull
  private Integer brandId;

  private String imageUri;

  @NotBlank
  @Size(max=255)
  private String name;

  @NotNull
  private boolean active;
}
