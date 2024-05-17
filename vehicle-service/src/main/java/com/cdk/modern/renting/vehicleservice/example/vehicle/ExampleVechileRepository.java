package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import java.util.UUID;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExampleVechileRepository extends R2dbcRepository<Vehicle, UUID>,ExampleCustomVehicleRepository {

}
