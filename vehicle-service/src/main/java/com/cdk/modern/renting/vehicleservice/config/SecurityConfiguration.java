package com.cdk.modern.renting.vehicleservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity.CsrfSpec;
import org.springframework.security.oauth2.server.resource.introspection.ReactiveOpaqueTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableReactiveMethodSecurity(useAuthorizationManager = true)
@EnableWebFluxSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http,
        ReactiveOpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter, OAuth2Properties oAuth2Properties
    ) {
        http.csrf(CsrfSpec::disable);
        http.oauth2ResourceServer(
          auth -> auth
              .opaqueToken(
                  opaqueTokenConfigurer -> opaqueTokenConfigurer
                      .introspectionUri(oAuth2Properties.getIntrospectionUri())
                      .introspectionClientCredentials(oAuth2Properties.getClientId(), oAuth2Properties.getClientSecret())
                      .authenticationConverter(opaqueTokenAuthenticationConverter)
              )
        );

        http.authorizeExchange(
            authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .anyExchange()
                    .permitAll()
        );

        return http.build();
    }

    @Bean
    public ReactiveOpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter() {
        return new CustomOpaqueTokenAuthenticationConverter();
    }
}
