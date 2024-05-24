package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.response.BrandResponse;
import reactor.core.publisher.Flux;

public interface BrandService {
    Flux<BrandResponse> getBrands();
}
