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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Tag(name="Metadata")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MetadataController {

    private final MetadataService metadataService;

    @Operation(summary = "Get types", description = "Returns all types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "type", produces = "application/json")
    Flux<TypeResponse> getTypes() {
        return null;
    }

    @Operation(summary = "Get brands", description = "Returns all brands")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "brand", produces = "application/json")
    Flux<BrandResponse> getBrands() {
        return null;
    }

    @Operation(summary = "Get types and brands", description = "Returns all types and brands")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "type-brand", produces = "application/json")
    Mono<TypeBrandResponse> getTypeAndBrands() {
        return null;
    }

    @Operation(summary = "Find model by filter", description = "Returns models after filter")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
        @ApiResponse(responseCode = "401", description = "Unauthorized - Token is invalid", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
    })
    @GetMapping(value = "model", produces = "application/json")
    Flux<ModelResponse> getModels(FindModelRequest request) {
        return metadataService.findModels(request);
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
    Mono<ModelResponse> newModel(CreateModelRequest request) {
        return null;
    }
}
