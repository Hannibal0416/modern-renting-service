package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.advice.ErrorResponse;
import com.cdk.modern.renting.vehicleservice.domain.Vehicle;
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleCreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleVehicleRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.VehicleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.stream.BaseStream;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.stream.Streams;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/example/vehicles")
@Tag(name = "Example")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ExampleVehicleController {

  private final ExampleVehicleService vehicleService;
  private final VehicleRepository vehicleRepository;

  @Operation(summary = "Get a vehicle by id", description = "Returns a vehicle")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The vehicle was not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping(value = "/{id}", produces = "application/json")
  //  @PreAuthorize("hasAnyAuthority('READ')")
  public Mono<ExampleVehicleResponse> getVehicle(@PathVariable UUID id) {
    return vehicleService.findById(id);
  }

  @Operation(summary = "Get vehicles by name", description = "Returns vehicles")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ExampleVehicleRequest.class))
            }),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The vehicles was not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping(value = "/byModelIdAndName", produces = "application/json")
  //  @PreAuthorize("hasAnyAuthority('READ')")
  public Flux<ExampleVehicleResponse> getVehicles(@Valid ExampleVehicleRequest request) {
    log.info(request.toString());
    List<ExampleVehicleResponse> vehicleResponseList =
        List.of(new ExampleVehicleResponse(), new ExampleVehicleResponse());
    return Flux.fromIterable(vehicleResponseList);
  }

  @GetMapping(produces = "application/json")
  //  @PreAuthorize("hasAnyAuthority('READ')")
  public Flux<ExampleVehicleResponse> getAll(
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "50") Integer limit) {
    return vehicleService.findAll(offset, limit);
  }

  @PostMapping(produces = "application/json", consumes = "application/json")
  @ResponseStatus(HttpStatus.CREATED)
  //  @PreAuthorize("hasAnyAuthority('UPDATE')")
  public Mono<ExampleVehicleResponse> create(@RequestBody ExampleCreateVehicleRequest request) {
    return vehicleService.save(request);
  }

  @GetMapping(value = "getFlux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  //  @PreAuthorize("hasAnyAuthority('READ')")
  public Flux<Vehicle> getFlux() {

    Flux<Vehicle> response =
        Flux.fromStream(
            IntStream.of(1, 2, 3, 4, 5)
                .mapToObj(
                    I -> {
                      try {
                        Thread.sleep(1000);
                      } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                      }
                      return Vehicle.builder().name("vehicle" + I).build();
                    }));

    return response;
  }

  @GetMapping(value = "getFlux2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<ExampleVehicleResponse> getFlux2() {
      return vehicleRepository.findAll().collectList().map( vehicles ->  Flux.fromStream(vehicles.stream().map( vehicle -> {
          ExampleVehicleResponse response = new ExampleVehicleResponse();
          BeanUtils.copyProperties(vehicle, response);
          try {
              Thread.sleep(1000);
          } catch (InterruptedException e) {
              throw new RuntimeException(e);
          }
          return response;
      }))).flatMapMany( m -> m);
  }



}
