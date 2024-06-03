package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.BrandResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.request.UpdateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;

  private final CustomVehicleRepository customVehicleRepository;

  private final TransactionalOperator transactionalOperator;

    @Override
    public Mono<VehicleResponse> get(UUID id) {
        return customVehicleRepository
                .findVehicleById(id)
                .switchIfEmpty(Mono.error(new DataRetrievalFailureException("Not Found")))
                .map(Optional::of)
                .flatMap(
                        optionalVehicle -> {
                            VehicleResponse vehicleResponse = new VehicleResponse();
                            ModelResponse modelResponse = new ModelResponse();
                            BrandResponse brandResponse = new BrandResponse();
                            TypeResponse typeResponse = new TypeResponse();
                            optionalVehicle.ifPresent(
                                    vehicle -> {
                                        BeanUtils.copyProperties(vehicle, vehicleResponse);
                                        Optional.ofNullable(vehicle.getModel())
                                                .ifPresent(
                                                        model -> {
                                                            BeanUtils.copyProperties(model, modelResponse);
                                                            Optional.ofNullable(model.getBrand())
                                                                    .ifPresent(
                                                                            brand -> BeanUtils.copyProperties(brand, brandResponse));
                                                            Optional.ofNullable(model.getType())
                                                                    .ifPresent(type -> BeanUtils.copyProperties(type, typeResponse));
                                                        });
                                    });

                            modelResponse.setBrand(brandResponse);
                            modelResponse.setType(typeResponse);
                            vehicleResponse.setModel(modelResponse);
                            return Mono.just(vehicleResponse);
                        });
    }

  @Override
  public Flux<VehicleResponse> getAll() {
      return vehicleRepository.findAll().flatMap(
              each -> {
                  VehicleResponse vehicleResponse = new VehicleResponse();
                  BeanUtils.copyProperties(each, vehicleResponse);
                  return Flux.just(vehicleResponse);
              }
      );
  }

  @Override
  public Mono<VehicleResponse> create(Mono<CreateVehicleRequest> requestMono) {

    return requestMono.flatMap(
        request -> {
          Vehicle vehicle = Vehicle.builder().build();
          BeanUtils.copyProperties(request, vehicle);
          return vehicleRepository.save(vehicle).map(this::toVehicleResponse);
        });
  }

  @Override
  public Mono<VehicleResponse> update(UUID id, UpdateVehicleRequest request) {
    return vehicleRepository
        .findById(id)
        .switchIfEmpty(Mono.error(new DataRetrievalFailureException("Vehicle not found")))
        .flatMap(
            existingVehicle -> {
              BeanUtils.copyProperties(request, existingVehicle);
              return vehicleRepository.save(existingVehicle);
            })
        .map(this::toVehicleResponse)
        .as(transactionalOperator::transactional);
  }

  private VehicleResponse toVehicleResponse(Vehicle vehicle) {
    VehicleResponse vehicleResponse = new VehicleResponse();
    BeanUtils.copyProperties(vehicle, vehicleResponse);
    return vehicleResponse;
  }
}
