package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.example.vehicle.ExampleCustomVehicleRepository;
import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface VehicleRepository extends R2dbcRepository<Vehicle, UUID> {
    Flux<Vehicle> findBy(Pageable pageable);
}
