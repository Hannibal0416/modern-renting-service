package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import java.util.UUID;
import reactor.core.publisher.Mono;

public interface ExampleCustomVehicleRepository {
  Mono<Vehicle> findVehicleById (UUID id);
}
