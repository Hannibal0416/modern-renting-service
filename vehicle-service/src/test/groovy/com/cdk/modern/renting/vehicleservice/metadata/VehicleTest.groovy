package com.cdk.modern.renting.vehicleservice.metadata


import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class VehicleTest extends Specification {
    @Autowired
    WebTestClient webTestClient

//    @SpringBean
//    VehicleRepository vehicleRepository = Mock()

//    @Shared
//    Mono<Vehicle> vehicleMono

    def baseUrl = "/vehicle"

//    void setupSpec() {
//        def vehicle = Vehicle.builder().name("mockedVehicle").build();
//    }

    def "when 'PostVehicle' is performed then the response should have a 201 status"() {
        given: "Create vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new CreateVehicleRequest()
        request.name = name
        request.modelId = modelId
        request.rentPrice = rentPrice

        when:
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateVehicleRequest).exchange()

        then:
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is2xxSuccessful()
            responseSpec.expectBody(VehicleResponse).consumeWith {
                result ->
                    {
                        assert result.responseBody.name == name
                        assert result.responseBody.rentPrice == rentPrice
                    }
            }
        }


        where:
        name       | modelId | rentPrice
        "vehicle1" | 1       |  100
        "vehicle2" | 2       |  200
        "vehicle2" | 3       |  300
    }
}