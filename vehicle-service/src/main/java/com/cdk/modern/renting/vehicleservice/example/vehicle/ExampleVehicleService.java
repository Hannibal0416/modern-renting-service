package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleCreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import java.util.UUID;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExampleVehicleService {
  Mono<ExampleVehicleResponse> findById(UUID id);

  Flux<ExampleVehicleResponse> findAll(Integer offset, Integer limit);

  Mono<ExampleVehicleResponse> save(ExampleCreateVehicleRequest request);

  Mono<ExampleVehicleResponse> saveFailure(ExampleCreateVehicleRequest request);
}
