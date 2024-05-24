package com.cdk.modern.renting.userservice

import com.cdk.modern.renting.userservice.user.UserController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import spock.lang.Specification

@SpringBootTest
@ActiveProfiles("test")
class LoadContextTest extends Specification {

    @Autowired
    private UserController userController

    def "when context is loaded then all expected beans are created"() {
        expect: "the UserController is created"
        userController
    }
}
