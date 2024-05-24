package com.cdk.modern.renting.vehicleservice.metadata


import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class VehicleTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    def baseUrl = "/vehicle"

    @WithMockUser(authorities = "WRITE")
    def "when 'PostVehicle' is performed then the response should have a 201 status"() {
        given: "Create vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new CreateVehicleRequest()
        request.name = name
        request.modelId = modelId
        request.rentPrice = rentPrice

        when: "Access 'PostVehicle'"
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateVehicleRequest).exchange()

        then: "Should have a 201 response status"
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
        "vehicle1" | 1       | 100
        "vehicle2" | 2       | 200
        "vehicle2" | 3       | 300
    }


    @WithMockUser(authorities = "WRITE")
    def "when 'PostVehicle' is performed then the response should have a 400 status"() {
        given: "Create vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new CreateVehicleRequest()
        request.name = name
        request.modelId = modelId
        request.rentPrice = rentPrice

        when: "Access 'PostVehicle'"
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateVehicleRequest).exchange()

        then:
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is4xxClientError()
        }


        where: "Should have a 400 response status"
        name       | modelId | rentPrice
        "vehicle1" | null    | 100
        null       | 2       | 200
        "vehicle2" | 3       | null
    }

    def "when 'PostVehicle' is performed without authorities then the response should have a 401 status"() {
        given: "Create vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new CreateVehicleRequest()

        when: "Access 'PostVehicle'"
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateVehicleRequest).exchange()

        then: "Should have a 401 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is4xxClientError()
        }

    }
}