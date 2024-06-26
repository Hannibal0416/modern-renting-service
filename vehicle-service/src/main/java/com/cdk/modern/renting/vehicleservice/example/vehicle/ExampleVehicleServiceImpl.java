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

import com.cdk.modern.renting.vehicleservice.metadata.BrandRepository;
import com.cdk.modern.renting.vehicleservice.metadata.ModelRepository;
import com.cdk.modern.renting.vehicleservice.metadata.TypeRepository;
import com.cdk.modern.renting.vehicleservice.vehicle.VehicleRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

  private final ExampleCustomVehicleRepository customVehicleRepository;
  private final VehicleRepository vehicleRepository;
  private final BrandRepository brandRepository;
  private final ModelRepository modelRepository;
  private final TypeRepository typeRepository;

  @Override
  public Mono<ExampleVehicleResponse> findById(UUID id) {
    return customVehicleRepository
        .findVehicleById(id)
        .switchIfEmpty(Mono.error(new DataRetrievalFailureException("Not Found")))
        .map(Optional::of)
        .flatMap(
            optionalVehicle -> {
              ExampleVehicleResponse vehicleResponse = new ExampleVehicleResponse();
              ExampleModelResponse modelResponse = new ExampleModelResponse();
              ExampleBrandResponse brandResponse = new ExampleBrandResponse();
              ExampleTypeResponse typeResponse = new ExampleTypeResponse();
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
  @CircuitBreaker(name="cloudBinderService")
  public Flux<ExampleVehicleResponse> findAll(Integer offset, Integer limit) {
    Pageable pageable = PageRequest.of(offset, limit, Sort.by("createdAt"));
    return vehicleRepository
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
    Type type = new Type();
    Optional.ofNullable(request.getModel())
        .ifPresent(
            model -> {
              Optional.ofNullable(model.getBrand())
                  .ifPresent(brandReq -> BeanUtils.copyProperties(brandReq, brand));
              Optional.ofNullable(model.getType())
                  .ifPresent(typeReq -> BeanUtils.copyProperties(typeReq, type));
            });

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
              Optional.ofNullable(request.getModel())
                  .ifPresent(
                      modelReq -> {
                        BeanUtils.copyProperties(modelReq, model);
                        model.setBrandId(tuple.getT1().getId());
                        model.setTypeId(tuple.getT2().getId());
                      });
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
                modelMono.flatMap(
                    savedModel -> {
                      Vehicle vehicle = new Vehicle();
                      BeanUtils.copyProperties(request, vehicle);
                      vehicle.setModelId(savedModel.getId());
                      ExampleVehicleResponse vehicleResponse = new ExampleVehicleResponse();
                      return vehicleRepository
                          .save(vehicle)
                          .map(
                              savedVehicle -> {
                                BeanUtils.copyProperties(savedVehicle, vehicleResponse);
                                return vehicleResponse;
                              });
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
                            vehicleRepository
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
