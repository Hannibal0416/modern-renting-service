package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VehicelServiceImpl implements VehicleService {

  private final VehicleRepository vehicleRepository;

  @Override
  public Mono<VehicleResponse> create(Mono<CreateVehicleRequest> requestMono) {

    return requestMono.flatMap(
        request -> {
          Vehicle vehicle = Vehicle.builder().build();
          BeanUtils.copyProperties(request, vehicle);
          return vehicleRepository
              .save(vehicle)
              .map(
                  savedVehicle -> {
                    VehicleResponse vehicleResponse = new VehicleResponse();
                    BeanUtils.copyProperties(savedVehicle, vehicleResponse);
                    return vehicleResponse;
                  });
        });
  }
}
