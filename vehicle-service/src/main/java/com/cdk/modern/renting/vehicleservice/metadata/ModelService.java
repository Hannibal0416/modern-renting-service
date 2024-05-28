package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.request.CreateModelRequest;
import com.cdk.modern.renting.vehicleservice.metadata.request.FindModelRequest;
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ModelService {
    Flux<ModelResponse> findModels(FindModelRequest request);
    Mono<ModelResponse> create(Mono<CreateModelRequest> request);
}
