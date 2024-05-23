package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import reactor.core.publisher.Mono;

public interface VehicleService {
  Mono<VehicleResponse> create(CreateVehicleRequest request);
}
