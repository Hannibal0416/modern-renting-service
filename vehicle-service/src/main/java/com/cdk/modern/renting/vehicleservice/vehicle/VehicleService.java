package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.request.UpdateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface VehicleService {
  Mono<VehicleResponse> get(UUID id);

  Flux<VehicleResponse> getAll();

  Mono<VehicleResponse> create(Mono<CreateVehicleRequest> request);

  Mono<VehicleResponse> update(UUID id, UpdateVehicleRequest request);
}
