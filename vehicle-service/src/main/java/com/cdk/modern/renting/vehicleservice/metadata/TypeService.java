package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.response.TypeResponse;
import reactor.core.publisher.Flux;

public interface TypeService {
    Flux<TypeResponse> getTypes();
}
