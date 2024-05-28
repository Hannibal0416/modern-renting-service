package com.cdk.modern.renting.vehicleservice.metadata

import com.cdk.modern.renting.vehicleservice.domain.Vehicle
import com.cdk.modern.renting.vehicleservice.vehicle.CustomVehicleRepository
import com.cdk.modern.renting.vehicleservice.vehicle.VehicleRepository
import com.cdk.modern.renting.vehicleservice.vehicle.request.CreateVehicleRequest
import com.cdk.modern.renting.vehicleservice.vehicle.request.UpdateVehicleRequest
import com.cdk.modern.renting.vehicleservice.vehicle.response.VehicleResponse
import org.spockframework.spring.SpringBean
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class VehicleUpdateTest extends Specification {

    @Autowired
    WebTestClient webTestClient

    UUID uuid
    String updateUrl

    @SpringBean
    VehicleRepository vehicleRepository = Mock()

    def setup() {
        uuid = UUID.randomUUID()
        updateUrl = "/vehicle/${uuid}"
    }

    @WithMockUser(authorities = "WRITE")
    def "when 'PutVehicle' is performed then the response should have a 201 status"() {
        given: "Update vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new UpdateVehicleRequest()
        request.name = name
        request.modelId = modelId
        request.rentPrice = rentPrice

        and: "An existing vehicle"
        def vehicle = new Vehicle(id: uuid, name: "oldName", modelId: 1, rentPrice: 1000)

        and: "VehicleRepository that return existing vehicle and save it"
        vehicleRepository.findById(uuid) >> Mono.just(vehicle)
        vehicleRepository.save(vehicle) >> Mono.just(vehicle)

        when: "Access 'PutVehicle'"
        def responseSpec = webTestClient.put().uri(updateUrl)
                .body(Mono.just(request), UpdateVehicleRequest).exchange()

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

        where: "Should have a 201 response status"
        name          | modelId | rentPrice
        "newVehicle1" | 1       | 1500
        "newVehicle2" | 2       | 2000
        "newVehicle3" | 3       | 3000
    }


    @WithMockUser(authorities = "WRITE")
    def "when 'PutVehicle' is performed then the response should have a 400 status"() {
        given: "Update vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new UpdateVehicleRequest()
        request.name = name
        request.modelId = modelId
        request.rentPrice = rentPrice

        when: "Access 'PutVehicle'"
        def responseSpec = webTestClient.put().uri(updateUrl)
                .body(Mono.just(request), UpdateVehicleRequest).exchange()

        then:
        verifyAll(responseSpec) {
            responseSpec.expectStatus().isBadRequest()
        }


        where: "Should have a 400 response status"
        name          | modelId | rentPrice
        "nv"          | 1       | 1500
        "newVehicle2" | 0       | 2000
        "newVehicle3" | 3       | null
        null          | 4       | 1000
    }

    @WithMockUser(authorities = "WRITE")
    def "when 'PutVehicle' is performed without existing vehicle then the response should have a 404 status"() {
        given: "Update vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new UpdateVehicleRequest()
        request.name = name
        request.modelId = modelId
        request.rentPrice = rentPrice

        and: "VehicleRepository that cannot find existing vehicle"
        vehicleRepository.findById(uuid) >> Mono.empty()

        when: "Access 'PutVehicle'"
        def responseSpec = webTestClient.put().uri(updateUrl)
                .body(Mono.just(request), UpdateVehicleRequest).exchange()

        then: "Should have a 404 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().isNotFound()
        }

        where:
        name          | modelId | rentPrice
        "newVehicle1" | 1       | 1500
        "newVehicle2" | 2       | 2000
        "newVehicle3" | 3       | 3000
    }

    def "when 'PutVehicle' is performed without authorities then the response should have a 403 status"() {
        given: "Update vehicle request: name: #name, modelId: #modelId, rentPrice: #rentPrice"
        def request = new UpdateVehicleRequest()

        when: "Access 'PutVehicle'"
        def responseSpec = webTestClient.put().uri(updateUrl)
                .body(Mono.just(request), UpdateVehicleRequest).exchange()

        then: "Should have a 403 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().isForbidden()
        }

    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class VehicleReadTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    UUID uuid
    String readUrl

    @SpringBean
    CustomVehicleRepository customVehicleRepository = Mock()

    def setup() {
        uuid = UUID.randomUUID()
        readUrl = "/vehicle"
    }

    @WithMockUser(authorities = "READ")
    def "when 'ReadVehicle' is performed then the response should have a 200 status" () {
        given: "Read vehicle request"
        def vehicle = new Vehicle(id: uuid, name: "test1", modelId: 1, rentPrice: 1000)
        and: "VehicleRepository that return existing vehicle"
        customVehicleRepository.findVehicleById(uuid) >> Mono.just(vehicle)

        when: "Access 'ReadVehicle'"
        def responseSpec = webTestClient.get().uri("${readUrl}/${uuid}").exchange()

        then: "Should have a 200 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is2xxSuccessful()
            responseSpec.expectBody(VehicleResponse).consumeWith {
                result ->
                    {
                        assert result.responseBody.id == vehicle.getId()
                        assert result.responseBody.name == vehicle.getName()
                    }
            }
        }

        where:
        name          | modelId | rentPrice
        "test1"       | 1       | 1000
    }

    @WithMockUser(authorities = "READ")
    def "when 'ReadVehicle' is performed without existing vehicle then the response should have a 404 status"() {
        given: "Read vehicle request"
        and: "VehicleRepository that cannot find existing vehicle"
        customVehicleRepository.findVehicleById(uuid) >> Mono.empty()

        when: "Access 'ReadVehicle'"
        def responseSpec = webTestClient.get().uri("${readUrl}/${uuid}").exchange()

        then: "Should have a 404 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().isNotFound()
        }
    }

    def "when 'ReadVehicle' is performed without authorities then the response should have a 403 status"() {
        given: "Read vehicle request"
        when: "Access 'ReadVehicle'"
        def responseSpec = webTestClient.get().uri("${readUrl}/${uuid}").exchange()

        then: "Should have a 403 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().isForbidden()
        }
    }
}