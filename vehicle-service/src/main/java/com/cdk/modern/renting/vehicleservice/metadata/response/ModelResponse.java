package com.cdk.modern.renting.vehicleservice.metadata.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModelResponse {
    private Integer id;

    private TypeResponse type;
    private Integer typeId;

    private BrandResponse brand;
    private Integer brandId;

    private String imageUri;
    private String name;
    private boolean active;
    protected LocalDateTime createdAt;
}
