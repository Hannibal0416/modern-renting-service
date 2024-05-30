package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface CustomVehicleRepository {
  Mono<Vehicle> findVehicleById(UUID id);
}
