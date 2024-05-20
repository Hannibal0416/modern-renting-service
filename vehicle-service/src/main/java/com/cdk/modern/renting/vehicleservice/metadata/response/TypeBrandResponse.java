package com.cdk.modern.renting.vehicleservice.metadata.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TypeBrandResponse {
    private List<TypeResponse> type;
    private List<BrandResponse> brand;
}
