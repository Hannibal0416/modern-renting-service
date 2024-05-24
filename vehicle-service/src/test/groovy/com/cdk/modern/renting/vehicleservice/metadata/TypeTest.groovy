package com.cdk.modern.renting.vehicleservice.metadata

import com.cdk.modern.renting.vehicleservice.domain.Type
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeResponse
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
class TypeTest extends Specification {
    @Autowired
    WebTestClient client

    @Shared
    Flux<Type> typeFlux

    def baseUrl = "/type"

    @SpringBean
    TypeRepository repo = Stub {
        findAll() >> typeFlux
    }

    void setupSpec() {
        typeFlux = Flux.just(
                Type.builder().name("type1").build(),
                Type.builder().name("type2").build()
        )
    }

    def "when findAllTypes is called, it should receive positive response"() {
        given: "A user need to retrieve list of types"
        when: "Endpoint gets called"
        def httpResponse = client.get().uri(baseUrl).exchange()

        then: "Response received should be 200 OK with 2 objects"
        verifyAll(httpResponse) {
            httpResponse.expectStatus().is2xxSuccessful()
            httpResponse.expectBody(List<TypeResponse>).consumeWith {
                result -> {
                    def res = result.getResponseBody()
                    assert res.size() == 2
                }
            }
        }
    }

}
