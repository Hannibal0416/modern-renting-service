package com.cdk.modern.renting.vehicleservice.example.vehicle;

import com.cdk.modern.renting.vehicleservice.advice.ErrorResponse;
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleVehicleRequest;
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/example/vehicles")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ExampleVehicleController {

  private final ExampleVehicleService vehicleService;

  @Operation(summary = "Get a vehicle by id", description = "Returns a vehicle")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
      @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
      @ApiResponse(responseCode = "404", description = "Not found - The vehicle was not found", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping(value = "/{id}", produces = "application/json")
//  @PreAuthorize("hasAnyAuthority('READ')")
  public Mono<ExampleVehicleResponse> getVehicle(@PathVariable UUID id) {
//    UUID uuid = UUID.fromString(id);
    return vehicleService.findById(id);
  }

  @Operation(summary = "Get vehicles by name", description = "Returns vehicles")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ExampleVehicleRequest.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
      @ApiResponse(responseCode = "404", description = "Not found - The vehicles was not found", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping(produces = "application/json")
//  @PreAuthorize("hasAnyAuthority('READ')")
  public Flux<ExampleVehicleResponse> getVehicles(@Valid ExampleVehicleRequest request) {
    log.info(request.toString());
    List<ExampleVehicleResponse> vehicleResponseList = List.of(
        new ExampleVehicleResponse(),
        new ExampleVehicleResponse()
    );
    return Flux.fromIterable(vehicleResponseList);
  }
}