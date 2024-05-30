package com.cdk.modern.renting.vehicleservice.metadata

import com.cdk.modern.renting.vehicleservice.domain.Type
import com.cdk.modern.renting.vehicleservice.domain.Brand
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeBrandResponse
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Flux
import spock.lang.Shared
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
class TypeBrandTest extends Specification {
    @Autowired
    WebTestClient client

    def baseUrl = "/type-brand"

    @Shared
    Flux<Type> typeFlux

    @Shared
    Flux<Brand> brandFlux

    @SpringBean
    TypeRepository typeRepo = Stub {
        findAll() >> typeFlux
    }

    @SpringBean
    BrandRepository brandRepo = Stub {
        findAll() >> brandFlux
    }

    void setupSpec() {
        typeFlux = Flux.just(
                Type.builder().name("type1").build(),
                Type.builder().name("type2").build()
        )
        brandFlux = Flux.just(
                Brand.builder().name("brand1").build(),
                Brand.builder().name("brand2").build()
        )
    }

    def "when getTypeAndBrands is called, it should receive positive response"() {
        given: "A user need to retrieve list of types"
        when: "Endpoint gets called"
        def httpResponse = client.get().uri(baseUrl).exchange()

        then: "Response received should be 200 OK with 2 objects"
        verifyAll(httpResponse) {
            httpResponse.expectStatus().is2xxSuccessful()
            httpResponse.expectBody(TypeBrandResponse).consumeWith {
                result -> {
                    def res = result.getResponseBody()

                    assert res.type.size() == 2
                    assert res.brand.size() == 2
                }
            }
        }
    }
}
