package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.vehicle.response.BrandResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.response.ModelResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.response.TypeResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleServiceImpl implements VehicleService{

  private final VechileRepository vechileRepository;

  @Override
  public Mono<VehicleResponse> findById(UUID id) {
    return vechileRepository.findVehicleById(id).map(Optional::of).defaultIfEmpty(Optional.empty())
        .flatMap(optionalVehicle -> {
          if (optionalVehicle.isPresent()) {
            VehicleResponse vehicleResponse = new VehicleResponse();
            BeanUtils.copyProperties(optionalVehicle.get(), vehicleResponse);
            ModelResponse modelResponse = new ModelResponse();
            BeanUtils.copyProperties(optionalVehicle.get().getModel(), modelResponse);
            BrandResponse brandResponse =new BrandResponse();
            BeanUtils.copyProperties(optionalVehicle.get().getModel().getBrand(), brandResponse);
            TypeResponse typeResponse = new TypeResponse();
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
