package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Model;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import io.r2dbc.spi.Row;
import java.util.UUID;
import java.util.function.BiFunction;

class ExampleVehicleMapper implements BiFunction<Row, Object, Vehicle> {

  public Vehicle apply(Row row, Object o) {
    UUID id = row.get("id", UUID.class);
    String imageUri = row.get("image_uri", String.class);
    String vehicleName = row.get("vehicle_name", String.class);
    String brandName = row.get("brand_name", String.class);
    String typeName = row.get("type_name", String.class);
    String modelName = row.get("model_name", String.class);

    Brand brand = Brand.builder().name(brandName).build();
    Type type = Type.builder().name(typeName).build();
    Model model = Model.builder().name(modelName).brand(brand).type(type).build();
    return Vehicle.builder().id(id).name(vehicleName).imageUri(imageUri).model(model).build();

  }
}
