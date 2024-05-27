package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
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
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicleServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;

  private final TransactionalOperator transactionalOperator;

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
