package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.advice.ErrorResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.request.VehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

  @Operation(summary = "Get a vehicle by id", description = "Returns a vehicle")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
      @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
      @ApiResponse(responseCode = "404", description = "Not found - The vehicle was not found", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @GetMapping(value = "/{id}", produces = "application/json")
  @PreAuthorize("hasAnyAuthority('READ')")
  public Mono<VehicleResponse> getVehicle(@PathVariable String id) {
    return Mono.just(new VehicleResponse());
  }

  @Operation(summary = "Get vehicles by name", description = "Returns vehicles")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = VehicleRequest.class))}),
      @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid"),
      @ApiResponse(responseCode = "404", description = "Not found - The vehicles was not found", content = {
          @Content(mediaType = "application/json",
              schema = @Schema(implementation = ErrorResponse.class))})
  })
  @PostMapping(produces = "application/json")
  @PreAuthorize("hasAnyAuthority('READ')")
  public Flux<VehicleResponse> getVehicles(@RequestBody @Valid VehicleRequest request) {
    List<VehicleResponse> vehicleResponseList = List.of(
        new VehicleResponse(),
        new VehicleResponse()
    );
    return Flux.fromIterable(vehicleResponseList);
  }
}
