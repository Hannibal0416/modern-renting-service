package com.cdk.modern.renting.userservice

import com.cdk.modern.renting.userservice.domain.Role
import com.cdk.modern.renting.userservice.domain.User
import com.cdk.modern.renting.userservice.user.RoleRepository
import com.cdk.modern.renting.userservice.user.UserRepository
import com.cdk.modern.renting.userservice.user.UserService
import com.cdk.modern.renting.userservice.user.request.UserCreateRequest
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude= SecurityAutoConfiguration.class)
class UserServiceTest extends Specification {

    @SpringBean
    UserRepository userRepository = Stub()

    @SpringBean
    RoleRepository roleRepository = Stub()

    @Autowired
    UserService userService

    def 'should add new user'() {
        given:
        UserCreateRequest user = new UserCreateRequest(username: "123")
        UserInfoResponse userInfo = new UserInfoResponse(username: "123")


        Role roleDomain = new Role(id: 1, name: "USER")
        Set<Role> roles = [roleDomain]

        User userDomain = new User(username: "123", roles: roles)

        userRepository.save(_ as User) >> userDomain
        roleRepository.findByName(_ as String) >> roleDomain

        when:
        UserInfoResponse result = userService.createUser(user)

        then:
        with(result) {
            username: 123
        }
    }
}