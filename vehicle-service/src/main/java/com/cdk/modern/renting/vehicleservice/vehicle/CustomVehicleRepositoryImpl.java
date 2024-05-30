package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomVehicleRepositoryImpl implements CustomVehicleRepository {

  private final DatabaseClient client;

  @Override
  public Mono<Vehicle> findVehicleById(UUID id) {
    String query =
        """
      SELECT
        v.id,
        v.image_uri,
        v.name AS vehicle_name,
        v.rent_price,
        v.color,
        v.production_year,
        v.seat_count,
        v.transmission,
        v.fuel_type,
        m.name AS model_name,
        t.name AS type_name,
        b.name AS brand_name
    FROM
        vehicle v
    LEFT JOIN
        vehicle_model m ON v.model_id = m.id
    LEFT JOIN
        vehicle_brand b ON m.brand_id = b.id
    LEFT JOIN
        vehicle_type t ON m.type_id = t.id
    WHERE
        v.id = :id;
        """;
    VehicleMapper mapper = new VehicleMapper();
    return client.sql(query).bind("id", id).map(mapper::apply).first();
  }
}
