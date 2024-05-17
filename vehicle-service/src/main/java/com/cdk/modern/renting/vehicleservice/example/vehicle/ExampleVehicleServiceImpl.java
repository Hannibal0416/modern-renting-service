package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Model;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleCreateTypeRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleCreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleBrandResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleModelResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleTypeResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ExampleVehicleServiceImpl implements ExampleVehicleService {

  private final ExampleVechileRepository vechileRepository;
  private final ExampleBrandRepository brandRepository;
  private final ExampleModelRepository modelRepository;
  private final ExampleTypeRepository typeRepository;

  @Override
  public Mono<ExampleVehicleResponse> findById(UUID id) {
    return vechileRepository.findVehicleById(id)
        .switchIfEmpty(Mono.error(new DataRetrievalFailureException("Not Found")))
        .map(Optional::of)
        .flatMap(optionalVehicle -> {
          ExampleVehicleResponse vehicleResponse = new ExampleVehicleResponse();
          BeanUtils.copyProperties(optionalVehicle.get(), vehicleResponse);
          ExampleModelResponse modelResponse = new ExampleModelResponse();
          BeanUtils.copyProperties(optionalVehicle.get().getModel(), modelResponse);
          ExampleBrandResponse brandResponse = new ExampleBrandResponse();
          BeanUtils.copyProperties(optionalVehicle.get().getModel().getBrand(), brandResponse);
          ExampleTypeResponse typeResponse = new ExampleTypeResponse();
          BeanUtils.copyProperties(optionalVehicle.get().getModel().getType(), typeResponse);
          modelResponse.setBrand(brandResponse);
          modelResponse.setType(typeResponse);
          vehicleResponse.setModel(modelResponse);
          return Mono.just(vehicleResponse);
        });
  }


  @Override
  public Flux<ExampleVehicleResponse> findAll(Integer offset, Integer limit) {
    Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt"));
    return vechileRepository.findBy(pageable)
        .flatMap(vehicle -> {
          ExampleVehicleResponse response = new ExampleVehicleResponse();
          BeanUtils.copyProperties(vehicle, response);
          return Mono.just(response);
        });
  }

  @Override
  @Transactional
  public Mono<ExampleVehicleResponse> save(ExampleCreateVehicleRequest request) {
    Vehicle vehicle = new Vehicle();
    BeanUtils.copyProperties(request, vehicle);
    Brand brand = new Brand();
    BeanUtils.copyProperties(request.getModel().getBrand(), brand);
    Mono<ExampleBrandResponse> brandResponseMono = brandRepository.save(brand).flatMap(saved -> {
      ExampleBrandResponse response = new ExampleBrandResponse();
      BeanUtils.copyProperties(saved, response);
      return Mono.just(response);
    });
    Type type = new Type();
    BeanUtils.copyProperties(request.getModel().getType(), type);
    Mono<ExampleTypeResponse> typeResponseMono = typeRepository.save(type).flatMap(saved -> {
      ExampleTypeResponse response = new ExampleTypeResponse();
      BeanUtils.copyProperties(saved, response);
      return Mono.just(response);
    });
    Model model = new Model();
    BeanUtils.copyProperties(request.getModel(), model);
    typeResponseMono.subscribe( t -> model.setTypeId(t.getId()));
    brandResponseMono.subscribe( b-> model.setBrandId(type.getId()));
    Mono<ExampleModelResponse> modelResponseMono = modelRepository.save(model).flatMap(saved -> {
      ExampleModelResponse response = new ExampleModelResponse();
      BeanUtils.copyProperties(saved, response);
      return Mono.just(response);
    });
    modelResponseMono.subscribe( m -> vehicle.setModelId(m.getId()));
    Mono<ExampleVehicleResponse> vehicleResponseMono = vechileRepository.save(vehicle).flatMap( saved ->{
      ExampleVehicleResponse response = new ExampleVehicleResponse();
      BeanUtils.copyProperties(saved, response);
      return Mono.just(response);
    });
    modelResponseMono.zipWith(typeResponseMono);
    modelResponseMono.zipWith(brandResponseMono);
    vehicleResponseMono.zipWith(modelResponseMono);

    return vehicleResponseMono;
  }
}
