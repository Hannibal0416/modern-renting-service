package com.cdk.modern.renting.vehicleservice.metadata.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class FindModelRequest {
  @Schema(name = "typeId", example = "100001", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Integer typeId;

  @Schema(name = "brandId", example = "100001", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  private Integer brandId;
}
