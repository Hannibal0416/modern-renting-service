package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Model;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleCreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleBrandResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleModelResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleTypeResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ExampleVehicleServiceImpl implements ExampleVehicleService {

  private final ExampleVechileRepository vechileRepository;
  private final ExampleBrandRepository brandRepository;
  private final ExampleModelRepository modelRepository;
  private final ExampleTypeRepository typeRepository;

  @Override
  public Mono<ExampleVehicleResponse> findById(UUID id) {
    return vechileRepository
        .findVehicleById(id)
        .switchIfEmpty(Mono.error(new DataRetrievalFailureException("Not Found")))
        .map(Optional::of)
        .flatMap(
            optionalVehicle -> {
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
    return vechileRepository
        .findBy(pageable)
        .flatMap(
            vehicle -> {
              ExampleVehicleResponse response = new ExampleVehicleResponse();
              BeanUtils.copyProperties(vehicle, response);
              return Mono.just(response);
            });
  }

  @Override
  //  @Transactional(rollbackFor = Exception.class)  //Option{name='readOnly', sensitive=false} +
  // isn't supported in H2 at the transaction level. You must set it on conenction URL. See
  // http://www.h2database.com/html/features.html#read_only
  //  @Transactional
  public Mono<ExampleVehicleResponse> save(ExampleCreateVehicleRequest request) {
    Brand brand = new Brand();
    BeanUtils.copyProperties(request.getModel().getBrand(), brand);
    Type type = new Type();
    BeanUtils.copyProperties(request.getModel().getType(), type);

    return brandRepository
        .save(brand)
        .map(
            savedBrand -> {
              ExampleBrandResponse brandResponse = new ExampleBrandResponse();
              BeanUtils.copyProperties(savedBrand, brandResponse);
              return brandResponse;
            })
        .zipWith(
            typeRepository
                .save(type)
                .map(
                    savedType -> {
                      ExampleTypeResponse typeResponse = new ExampleTypeResponse();
                      BeanUtils.copyProperties(savedType, typeResponse);
                      return typeResponse;
                    }))
        .map(
            tuple -> {
              Model model = new Model();
              BeanUtils.copyProperties(request.getModel(), model);
              model.setBrandId(tuple.getT1().getId());
              model.setTypeId(tuple.getT2().getId());
              return modelRepository
                  .save(model)
                  .map(
                      savedModel -> {
                        ExampleModelResponse modelResponse = new ExampleModelResponse();
                        BeanUtils.copyProperties(savedModel, modelResponse);
                        return modelResponse;
                      });
            })
        .flatMap(
            modelMono ->
                modelMono.map(
                    savedModel -> {
                      Vehicle vehicle = new Vehicle();
                      BeanUtils.copyProperties(request, vehicle);
                      vehicle.setModelId(savedModel.getId());
                      ExampleVehicleResponse vehicleResponse = new ExampleVehicleResponse();
                      vechileRepository
                          .save(vehicle)
                          .map(
                              savedVehicle -> {
                                BeanUtils.copyProperties(savedVehicle, vehicleResponse);
                                return vehicleResponse;
                              });
                      return vehicleResponse;
                    }))
        .doOnError(ex -> log.error("err :", ex));
  }

  @Override
  //  @Transactional
  public Mono<ExampleVehicleResponse> saveFailure(ExampleCreateVehicleRequest request) {
    Vehicle vehicle = new Vehicle();
    BeanUtils.copyProperties(request, vehicle);
    Brand brand = new Brand();
    BeanUtils.copyProperties(request.getModel().getBrand(), brand);

    Mono<ExampleBrandResponse> brandResponseMono =
        brandRepository
            .save(brand)
            .map(
                savedBrand -> {
                  ExampleBrandResponse brandResponse = new ExampleBrandResponse();
                  BeanUtils.copyProperties(savedBrand, brandResponse);
                  return brandResponse;
                });

    Type type = new Type();
    BeanUtils.copyProperties(request.getModel().getType(), type);
    Mono<ExampleVehicleResponse> vehicleResponseMono =
        typeRepository
            .save(type)
            .flatMap(
                savedType -> {
                  ExampleTypeResponse typeResponse = new ExampleTypeResponse();
                  ExampleModelResponse modelResponse = new ExampleModelResponse();
                  ExampleVehicleResponse vehicleResponse = new ExampleVehicleResponse();

                  BeanUtils.copyProperties(savedType, typeResponse);
                  Model model = new Model();
                  BeanUtils.copyProperties(request.getModel(), model);
                  brandResponseMono.subscribe(
                      brandResponse -> model.setBrandId(brandResponse.getId()));
                  model.setTypeId(savedType.getId());
                  modelRepository
                      .save(model)
                      .subscribe(
                          savedModel -> {
                            BeanUtils.copyProperties(savedModel, modelResponse);
                            vehicle.setModelId(savedModel.getId());
                            vechileRepository
                                .save(vehicle)
                                .subscribe(
                                    savedVehicl -> {
                                      BeanUtils.copyProperties(savedVehicl, vehicleResponse);
                                      modelResponse.setType(typeResponse);
                                      vehicleResponse.setModel(modelResponse);
                                    });
                          });
                  return Mono.just(vehicleResponse);
                })
            .doOnError(ex -> log.error("err :", ex));

    return vehicleResponseMono
        .zipWith(brandResponseMono)
        .flatMap(
            tuple -> {
              tuple.getT1().getModel().setBrand(tuple.getT2());
              return Mono.just(tuple.getT1());
            });
  }
}
