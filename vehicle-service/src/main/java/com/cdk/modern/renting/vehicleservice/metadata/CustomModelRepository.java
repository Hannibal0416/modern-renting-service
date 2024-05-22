package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Model;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CustomModelRepository {
  Flux<Model> findModels(Integer brandId, Integer typeId);
}


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class CustomModelRepositoryImpl implements CustomModelRepository {

  private final DatabaseClient client;

  @Override
  public Flux<Model> findModels(Integer brandId, Integer typeId) {
    String query =
            """
            SELECT m.id as model_id,
                  m.image_uri as model_image_uri,
                  m.name as model_name,
                  m.active as model_active,
                  m.created_at as model_created_at,
                  b.id as brand_id ,
                  b.image_uri as brand_image_uri ,
                  b.name brand_name,
                  b.active brand_active,
                  b.created_at brand_created_at,
                  t.id as type_id ,
                  t.image_uri as type_image_uri ,
                  t.name type_name,
                  t.active type_active,
                  t.created_at type_created_at
            FROM  vehicle_model m LEFT JOIN vehicle_brand b on m.brand_id = b.id LEFT JOIN vehicle_type t on m.type_id = t.id
            WHERE (:brand_id::INTEGER = NULL OR m.brand_id = :brand_id)
                OR (:type_id::INTEGER = NULL OR m.type_id = :type_id)
            """;
      DatabaseClient.GenericExecuteSpec genericExecuteSpec = client.sql(query);
      genericExecuteSpec = brandId != null ? genericExecuteSpec.bind("brand_id", brandId) :genericExecuteSpec.bindNull("brand_id", Integer.class);
      genericExecuteSpec = typeId != null ? genericExecuteSpec.bind("type_id", typeId) :genericExecuteSpec.bindNull("type_id", Integer.class);

    return genericExecuteSpec.map(
                    (row, obj) -> {
                      Brand brand =
                              Brand.builder()
                                      .id(row.get("brand_id", Integer.class))
                                      .imageUri(row.get("brand_image_uri", String.class))
                                      .name(row.get("brand_name", String.class))
                                      .active(Boolean.TRUE.equals(row.get("brand_active", Boolean.class)))
                                      .createdAt(row.get("brand_created_at", LocalDateTime.class))
                                      .build();
                      Type type =
                              Type.builder()
                                      .id(row.get("type_id", Integer.class))
                                      .imageUri(row.get("type_image_uri", String.class))
                                      .name(row.get("type_name", String.class))
                                      .active(Boolean.TRUE.equals(row.get("type_active", Boolean.class)))
                                      .createdAt(row.get("type_created_at", LocalDateTime.class))
                                      .build();
                      return Model.builder()
                              .id(row.get("model_id", Integer.class))
                              .imageUri(row.get("model_image_uri", String.class))
                              .name(row.get("model_name", String.class))
                              .active(Boolean.TRUE.equals(row.get("model_active", Boolean.class)))
                              .createdAt(row.get("model_created_at", LocalDateTime.class))
                              .brandId(brand.getId())
                              .brand(brand)
                              .typeId(type.getId())
                              .type(type)
                              .build();
                    })
            .all();
  }
}