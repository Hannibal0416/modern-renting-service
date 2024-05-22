package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.request.FindModelRequest;
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse;
import reactor.core.publisher.Flux;

public interface ModelService {
    Flux<ModelResponse> findModels(FindModelRequest request);
}
