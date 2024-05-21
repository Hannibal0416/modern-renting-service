package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;

import java.util.UUID;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ExampleVehicleRepository extends R2dbcRepository<Vehicle, UUID>,ExampleCustomVehicleRepository {

  Flux<Vehicle> findBy(Pageable pageable);
}
