package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.response.TypeBrandResponse;
import reactor.core.publisher.Mono;

public interface TypeBrandService {
    Mono<TypeBrandResponse> getTypeBrand();
}
