package com.cdk.modern.renting.userservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.introspection.OpaqueTokenAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
        OpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter, OAuth2Properties oAuth2Properties
    ) throws Exception {
        http.oauth2ResourceServer(
          auth -> auth
              .opaqueToken(
                  opaqueTokenConfigurer -> opaqueTokenConfigurer
                      .introspectionUri(oAuth2Properties.getIntrospectionUri())
                      .introspectionClientCredentials(oAuth2Properties.getClientId(), oAuth2Properties.getClientSecret())
                      .authenticationConverter(opaqueTokenAuthenticationConverter)
              )
        );

        http.authorizeHttpRequests(
            authorizationManagerRequestMatcherRegistry ->
                authorizationManagerRequestMatcherRegistry
                    .requestMatchers(HttpMethod.GET,"/users").authenticated()
                    .requestMatchers(HttpMethod.PUT,"/users").authenticated()
                    .anyRequest().permitAll()
        );

        return http.build();
    }

    @Bean
    public OpaqueTokenAuthenticationConverter opaqueTokenAuthenticationConverter() {
        return new CustomOpaqueTokenAuthenticationConverter();
    }

    @Bean
    public RestTemplate restTemplate(OAuth2Properties oAuth2Properties) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(oAuth2Properties.getClientId(), oAuth2Properties.getClientSecret()));
        return restTemplate;
    }
}
