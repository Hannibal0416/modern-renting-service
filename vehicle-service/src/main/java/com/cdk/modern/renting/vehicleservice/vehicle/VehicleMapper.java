package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Model;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;

class VehicleMapper implements BiFunction<Row, Object, Vehicle> {

  public Vehicle apply(Row row, Object o) {
    UUID id = row.get("id", UUID.class);
    String imageUri = row.get("image_uri", String.class);
    String modelName = row.get("model_name", String.class);
    String vehicleName = row.get("vehicle_name", String.class);
    Long rentPrice = row.get("rent_price", Long.class);
    String color = row.get("color", String.class);
    Short productionYear = row.get("production_year", Short.class);
    Short seatCount = row.get("seat_count", Short.class);
    String transmission = row.get("transmission", String.class);
    String fuelType = row.get("fuel_type", String.class);
    String brandName = row.get("brand_name", String.class);
    String typeName = row.get("type_name", String.class);

    Brand brand = Brand.builder().name(brandName).build();
    Type type = Type.builder().name(typeName).build();
    Model model = Model.builder().name(modelName).brand(brand).type(type).build();
    return Vehicle.builder()
        .id(id)
        .name(vehicleName)
        .imageUri(imageUri)
        .fuelType(fuelType)
        .rentPrice(rentPrice == null ? -1 : rentPrice.intValue())
        .color(color)
        .productionYear(productionYear)
        .seatCount(seatCount)
        .transmission(transmission)
        .model(model)
        .build();
  }
}
