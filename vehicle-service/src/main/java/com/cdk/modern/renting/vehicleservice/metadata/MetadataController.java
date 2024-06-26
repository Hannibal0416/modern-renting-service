package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.advice.ErrorResponse;
import com.cdk.modern.renting.vehicleservice.metadata.request.CreateModelRequest;
import com.cdk.modern.renting.vehicleservice.metadata.request.FindModelRequest;
import com.cdk.modern.renting.vehicleservice.metadata.response.BrandResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeBrandResponse;
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Tag(name="Metadata")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MetadataController {

    private final ModelService modelService;
    private final TypeService typeService;
    private final BrandService brandService;
    private final TypeBrandService typeBrandService;

    @Operation(summary = "Get types", description = "Returns all types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "type", produces = "application/json")
    Flux<TypeResponse> getTypes() {
        return typeService.getTypes();
    }

    @Operation(summary = "Get brands", description = "Returns all brands")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "brand", produces = "application/json")
    Flux<BrandResponse> getBrands() {
        return brandService.getBrands();
    }

    @Operation(summary = "Get types and brands", description = "Returns all types and brands")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "type-brand", produces = "application/json")
    Mono<TypeBrandResponse> getTypeAndBrands() {
        return typeBrandService.getTypeBrand();
    }

    @Operation(summary = "Find model by filter", description = "Returns models after filter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "model", produces = "application/json")
    Flux<ModelResponse> getModels(FindModelRequest request) {
        return modelService.findModels(request);
    }

    @Operation(summary = "Create new model", description = "Create new model")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Successfully created"),
        @ApiResponse(responseCode = "400", description = "Bad request", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @PostMapping(value = "model", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasAnyAuthority('WRITE')")
    @ResponseStatus(HttpStatus.CREATED)
    Mono<ModelResponse> newModel(@Valid @RequestBody Mono<CreateModelRequest> request) {
        return modelService.create(request);
    }
}
