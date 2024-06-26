package com.cdk.modern.renting.vehicleservice.metadata

import com.cdk.modern.renting.vehicleservice.domain.Brand
import com.cdk.modern.renting.vehicleservice.domain.Model
import com.cdk.modern.renting.vehicleservice.domain.Type
import com.cdk.modern.renting.vehicleservice.metadata.request.CreateModelRequest
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ModelTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    @Shared
    Flux<Model> modelFlux

    def baseModelUrl = "/model";

    @SpringBean
    ModelRepository modelRepository = Stub {
        findModels(_ as Integer, _ as Integer) >> modelFlux
    }

    void setupSpec() {
        def type = Type.builder().name("mockedType").build()
        def brand = Brand.builder().name("mockedType").build()
        modelFlux = Flux.just(Model.builder().name("mockedModel1").type(type).brand(brand).build(),
                Model.builder().name("mockedModel1").type(type).brand(brand).build())
    }

    def "when 'find models by filters' is performed then the response should have a 200 status"() {
        given: "A user needs to retrieve the model details"
        baseModelUrl += "?brandId=" + brandId + "&typeId=" + typeId
        when: "Accessing the 'find models by filters' endpoint"
        def responseSpec = webTestClient.get().uri(baseModelUrl).exchange()

        then: "the response has a 200 status and the size of the response is 2"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is2xxSuccessful()
            responseSpec.expectBody(List<ModelResponse>).consumeWith {
                result ->
                    {
                        def modelResponseList = result.getResponseBody()
                        assert modelResponseList.size() == 2
                    }
            }
        }
        where:
        brandId << 1
        typeId << 1
    }
}

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class ModelCreateTest extends Specification {
    @Autowired
    WebTestClient webTestClient

    def baseUrl = "/model"

    @WithMockUser(authorities = "WRITE")
    def "when 'PostModel' is performed then the response should have a 201 status"() {
        given: "Create model request: name: #name, typeId: #typeId, brandId: #brandId, active: #active"
        def request = new CreateModelRequest()
        request.typeId = typeId
        request.brandId = brandId
        request.name = name
        request.active = active

        when: "Access 'PostModel'"
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateModelRequest).exchange()

        then: "Should have a 201 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().isCreated()
            responseSpec.expectBody(ModelResponse).consumeWith {
                result ->
                    {
                        assert result.responseBody.name == name
                        assert result.responseBody.typeId == typeId
                        assert result.responseBody.brandId == brandId
                    }
            }
        }


        where:
        name     | typeId | brandId | active
        "model1" | 100005 | 100006  | true
    }


    @WithMockUser(authorities = "WRITE")
    def "when 'PostModel' is performed then the response should have a 400 status"() {
        given: "Create model request: name: #name, typeId: #typeId, brandId: #brandId, active: #active"
        def request = new CreateModelRequest()
        request.typeId = typeId
        request.brandId = brandId
        request.name = name
        request.active = active

        when: "Access 'PostModel'"
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateModelRequest).exchange()

        then:
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is4xxClientError()
        }


        where: "Should have a 400 response status"
        name     | typeId | brandId | active
        "model1" | null   | 100006  | true
        "model2" | 100005 | null    | true
        null     | 100005 | 100006  | true
    }

    def "when 'PostModel' is performed without authorities then the response should have a 401 status"() {
        given: "Create model request: name: #name, typeId: #typeId, brandId: #brandId, active: #active"
        def request = new CreateModelRequest()

        when: "Access 'PostModel'"
        def responseSpec = webTestClient.post().uri(baseUrl)
                .body(Mono.just(request), CreateModelRequest).exchange()

        then: "Should have a 401 response status"
        verifyAll(responseSpec) {
            responseSpec.expectStatus().is4xxClientError()
        }

    }
}