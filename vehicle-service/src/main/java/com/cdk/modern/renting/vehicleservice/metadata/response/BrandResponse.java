package com.cdk.modern.renting.vehicleservice.metadata.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BrandResponse {
    private Integer id;
    private String imageUri;
    private String name;
    private boolean active;
    protected LocalDateTime createdAt;
}
