package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ExampleVehicleService {
  Mono<ExampleVehicleResponse> findById(UUID id);
}
