package com.cdk.modern.renting.vehicleservice.example.vehicle

import com.cdk.modern.renting.vehicleservice.domain.Brand
import com.cdk.modern.renting.vehicleservice.domain.Model
import com.cdk.modern.renting.vehicleservice.domain.Type
import com.cdk.modern.renting.vehicleservice.domain.Vehicle
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleCreateVehicleRequest
import com.cdk.modern.renting.vehicleservice.example.vehicle.request.ExampleVehicleRequest
import com.cdk.modern.renting.vehicleservice.example.vehicle.response.ExampleVehicleResponse
import com.cdk.modern.renting.vehicleservice.metadata.BrandRepository
import com.cdk.modern.renting.vehicleservice.metadata.ModelRepository
import com.cdk.modern.renting.vehicleservice.metadata.TypeRepository
import com.cdk.modern.renting.vehicleservice.vehicle.VehicleRepository
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

import org.springframework.data.domain.Pageable;

//@WebFluxTest(controllers = ExampleVehicleController.class,excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ExampleVehicleControllerIntegrationTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    @SpringBean
    ExampleCustomVehicleRepository exampleCustomVehicleRepository = Mock()

    @SpringBean
    VehicleRepository vehicleRepository = Mock()

    @SpringBean
    ModelRepository modelRepository = Mock()

    @SpringBean
    TypeRepository typeRepository = Mock()

    @SpringBean
    BrandRepository brandRepository = Mock()

    def baseUrl = "/example/vehicles";

    @Shared
    Mono<Vehicle> mockedVehicleMono;

    @Shared
    Flux<Vehicle> mockedVehicleFlux;

    void setupSpec() {
        def type = Type.builder().name("mockedType").build()
        def brand = Brand.builder().name("mockedType").build()
        def model = Model.builder().name("mockedModel").type(type).brand(brand).build()
        def vehicle = Vehicle.builder().name("mockedVehicle").model(model).build()

        mockedVehicleMono = Mono.just(vehicle)
        mockedVehicleFlux = Flux.just(Vehicle.builder().name("mockedVehicle1").model(model).build(),
                Vehicle.builder().name("mockedVehicle2").model(model).build(),
                Vehicle.builder().name("mockedVehicle3").model(model).build())
    }

    def "when 'getVehicleById' is performed then the response has a 200 status"() {
        given: "A user needs to retrieve the vehicle details"
        exampleCustomVehicleRepository.findVehicleById(_ as UUID) >> mockedVehicleMono
        def url = baseUrl + "/" + UUID.randomUUID().toString()

        when: "Accessing the getVehicleById endpoint"
        def responseSpec = webTestClient.get().uri(url).exchange()

        then: "the response has a 200 status and the vehicle name is equal to 'mockedVehicle'"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is2xxSuccessful()
            responseSpec.expectBody(ExampleVehicleResponse).consumeWith {
                result ->
                    {
                        def vehicleResponse = result.getResponseBody()
                        assert vehicleResponse.name == "mockedVehicle"
                    }
            }
        }
    }

    def "when 'getVehicles' is performed then the response has a 200 status"() {
        given: "A user needs to retrieve all vehicle details"
        vehicleRepository.findBy(_ as Pageable) >> mockedVehicleFlux
        def url = baseUrl

        when: "Accessing the getVehicles endpoint"
        def responseSpec = webTestClient.get().uri(url).exchange()

        then: "the response has a 200 status and the vehicle name is equal to 'mockedVehicle'"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is2xxSuccessful()
            responseSpec.expectBody(List<ExampleVehicleResponse>).consumeWith {
                result ->
                    {
                        def vehicleResponseList = result.getResponseBody()
                        assert vehicleResponseList.size() == 3
                    }
            }
        }
    }

    def "when 'postVehicles' is performed then the response has a 201 status and the vehicle id is equal to #uuid"() {
        given: "A user creates a new vehicle with details"

        typeRepository.save(_ as Type) >> Mono.just(Type.builder().id(1).name("type").build())
        brandRepository.save(_ as Brand) >> Mono.just(Brand.builder().id(2).name("brand").build())
        modelRepository.save(_ as Model) >> Mono.just(Model.builder().id(3).name("model").build())
        vehicleRepository.save(_ as Vehicle) >> Mono.just(Vehicle.builder().id(uuid).name("vehicle").build())
        def url = baseUrl

        when: "Accessing the postVehicles endpoint"
        def request = new ExampleCreateVehicleRequest()
        request.name = "vehicle"
        def responseSpec = webTestClient.post().uri(url).body(Mono.just(request), ExampleCreateVehicleRequest).exchange()

        then: "the response has a 201 status and the vehicle id is equal to #uuid"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is2xxSuccessful()
            responseSpec.expectBody(ExampleVehicleResponse).consumeWith {
                result ->
                    {
                        def vehicleResponse = result.getResponseBody()
                        assert vehicleResponse.id == uuid
                    }
            }
        }
        where:
        uuid << UUID.randomUUID();
    }


}