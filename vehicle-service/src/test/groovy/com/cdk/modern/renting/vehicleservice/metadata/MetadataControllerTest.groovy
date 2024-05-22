package com.cdk.modern.renting.vehicleservice.metadata

import com.cdk.modern.renting.vehicleservice.domain.Brand
import com.cdk.modern.renting.vehicleservice.domain.Model
import com.cdk.modern.renting.vehicleservice.domain.Type
import com.cdk.modern.renting.vehicleservice.metadata.response.ModelResponse
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class MetadataControllerTest extends Specification {
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
        baseModelUrl += "?brandId=1&typeId=1"
        when: ""
        def responseSpec = webTestClient.get().uri(baseModelUrl).exchange()

        then: ""
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
    }
}