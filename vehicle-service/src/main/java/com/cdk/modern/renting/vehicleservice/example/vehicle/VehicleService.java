package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.example.vehicle.response.VehicleResponse;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface VehicleService {
  Mono<VehicleResponse> findById(UUID id);
}
