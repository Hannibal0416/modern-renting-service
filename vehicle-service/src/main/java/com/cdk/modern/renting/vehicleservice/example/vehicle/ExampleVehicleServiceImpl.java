package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleBrandResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleModelResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleTypeResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExampleVehicleServiceImpl implements ExampleVehicleService{

  private final ExampleVechileRepository vechileRepository;

  @Override
  public Mono<ExampleVehicleResponse> findById(UUID id) {
    return vechileRepository.findVehicleById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
        .flatMap(optionalVehicle -> {
          if (optionalVehicle.isPresent()) {
            ExampleVehicleResponse vehicleResponse = new ExampleVehicleResponse();
            BeanUtils.copyProperties(optionalVehicle.get(), vehicleResponse);
            ExampleModelResponse modelResponse = new ExampleModelResponse();
            BeanUtils.copyProperties(optionalVehicle.get().getModel(), modelResponse);
            ExampleBrandResponse brandResponse =new ExampleBrandResponse();
            BeanUtils.copyProperties(optionalVehicle.get().getModel().getBrand(), brandResponse);
            ExampleTypeResponse typeResponse = new ExampleTypeResponse();
            BeanUtils.copyProperties(optionalVehicle.get().getModel().getType(), typeResponse);
            modelResponse.setBrand(brandResponse);
            modelResponse.setType(typeResponse);
            vehicleResponse.setModel(modelResponse);
            return Mono.just(vehicleResponse);
          }
          return Mono.empty();
        });
  }
}
