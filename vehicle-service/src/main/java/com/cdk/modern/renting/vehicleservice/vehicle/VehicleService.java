package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface VehicleService {
  Mono<VehicleResponse> findById(UUID id);
}
