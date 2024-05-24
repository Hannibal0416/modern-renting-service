package com.cdk.modern.renting.vehicleservice.metadata

import com.cdk.modern.renting.vehicleservice.domain.Brand
import com.cdk.modern.renting.vehicleservice.metadata.response.BrandResponse
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
class BrandTest extends Specification {
    @Autowired
    WebTestClient client

    @Shared
    Flux<Brand> brandFlux

    def baseUrl = "/brand"

    @SpringBean
    BrandRepository repo = Stub {
        findAll() >> brandFlux
    }

    void setupSpec() {
        brandFlux = Flux.just(
                Brand.builder().name("brand1").build(),
                Brand.builder().name("brand2").build()
        )
    }

    def "when findAllBrands is called, it should receive positive response"() {
        given: "A user need to retrieve list of brands"
        when: "Endpoint gets called"
        def httpResponse = client.get().uri(baseUrl).exchange()

        then: "Response received should be 200 OK with 2 objects"
        verifyAll(httpResponse) {
            httpResponse.expectStatus().is2xxSuccessful()
            httpResponse.expectBody(List<BrandResponse>).consumeWith {
                result -> {
                    def res = result.getResponseBody()
                    assert res.size() == 2
                }
            }
        }
    }

}
