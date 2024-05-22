package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.request.FindModelRequest;
import com.cdk.modern.renting.vehicleservice.metadata.response.BrandResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class MetadataServiceImpl implements MetadataService {

  private final ModelRepository modelRepository;

  @Override
  public Flux<ModelResponse> findModels(FindModelRequest request) {
    return modelRepository
        .findModels(request.getBrandId(), request.getTypeId())
        .flatMap(
            model -> {
              ModelResponse modelResponse = new ModelResponse();
              TypeResponse typeResponse = new TypeResponse();
              BrandResponse brandResponse = new BrandResponse();
              Optional.ofNullable(model.getBrand())
                  .ifPresent(brand -> BeanUtils.copyProperties(brand, brandResponse));
              Optional.ofNullable(model.getType())
                  .ifPresent(type -> BeanUtils.copyProperties(type, typeResponse));
              BeanUtils.copyProperties(model, modelResponse);
              modelResponse.setBrand(brandResponse);
              modelResponse.setType(typeResponse);
              return Mono.just(modelResponse);
            });
  }
}
