package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Model;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;

public class VehicleMapper implements BiFunction<Row, Object, Vehicle> {

  public Vehicle apply(Row row, Object o) {
    UUID id = row.get("id", UUID.class);
    String imageUri = row.get("image_uri", String.class);
    String vehicleName = row.get("vehicle_name", String.class);
    String brandName = row.get("brand_name", String.class);
    String typeName = row.get("type_name", String.class);
    String modelName = row.get("model_name",String.class);
    Vehicle vehicle = new Vehicle();
    Model model = new Model();
    model.setName(modelName);
    Brand brand = new Brand();
    brand.setName(brandName);
    model.setBrand(brand);
    Type type = new Type();
    type.setName(typeName);
    model.setType(type);
    vehicle.setId(id);
    vehicle.setName(vehicleName);
    vehicle.setImageUri(imageUri);
    vehicle.setModel(model);
    return vehicle;
  }
}
