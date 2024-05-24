package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExampleCustomVehicleRepositoryImpl implements ExampleCustomVehicleRepository {

  private final DatabaseClient client;

  @Override
  public Mono<Vehicle> findVehicleById(UUID id) {
    String query = """
        SELECT v.id, v.image_uri, v.name as vehicle_name, m.name as model_name, t.name as type_name, b.name as brand_name
        FROM vehicle v LEFT JOIN vehicle_model m ON v.model_id = m.id LEFT JOIN vehicle_brand b on m.brand_id = b.id LEFT JOIN vehicle_type t on m.type_id = t.id
        WHERE v.id = :id
        """;
    ExampleVehicleMapper mapper = new ExampleVehicleMapper();
    return client.sql(query).bind("id", id).map(mapper::apply).first();
  }
}
