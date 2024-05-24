package com.cdk.modern.renting.vehicleservice.vehicle;

import com.cdk.modern.renting.vehicleservice.advice.ErrorResponse;
import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.request.FindVehiclesRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.request.UpdateVehicleRequest;
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.WebExchangeBindException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Tag(name = "Vehicle")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VehicleController {

  private final VehicleService vehicleService;

  @Operation(summary = "Get a vehicle by id", description = "Returns a vehicle")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Token is invalid",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The vehicle was not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping(value = "vehicle/{id}", produces = "application/json")
  public Mono<VehicleResponse> getVehicle(@PathVariable UUID id) {
    return null;
  }

  @Operation(
      summary = "Get vehicles by filter with inclusive operator (AND)",
      description = "Returns vehicles")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Token is invalid",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "404",
            description = "Not found - The vehicles was not found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            })
      })
  @GetMapping(value = "/vehicles", produces = "application/json")
  public Flux<VehicleResponse> getVehicles(
      @RequestParam(defaultValue = "0") Integer offset,
      @RequestParam(defaultValue = "50") Integer limit,
      @Valid FindVehiclesRequest request) {
    return null;
  }

  @Operation(summary = "Create new vehicle", description = "Create new vehicle")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Successfully created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Token is invalid",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
      })
  @PostMapping(value = "vehicle", produces = "application/json", consumes = "application/json")
  @PreAuthorize("hasAnyAuthority('WRITE')")
  public Mono<VehicleResponse> create(@Valid @RequestBody Mono<CreateVehicleRequest> request) {
    return vehicleService.create(request);
  }

  @Operation(summary = "Update existing vehicle", description = "Update vehicle")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Successfully created"),
        @ApiResponse(
            responseCode = "400",
            description = "Bad request",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized - Token is invalid",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
      })
  @PutMapping(value = "vehicle/{id}", produces = "application/json", consumes = "application/json")
  //  @PreAuthorize("hasAnyAuthority('WRITE')")
  public Mono<VehicleResponse> update(
      @PathVariable UUID id, @Valid @RequestBody UpdateVehicleRequest request) {
    return null;
  }
}
