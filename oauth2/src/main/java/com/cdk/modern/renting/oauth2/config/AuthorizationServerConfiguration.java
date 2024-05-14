package com.cdk.modern.renting.oauth2.config;


import com.cdk.modern.renting.oauth2.users.grantpassword.AuthorizationGrantTypePassword;
import com.cdk.modern.renting.oauth2.users.grantpassword.GrantPasswordAuthenticationProvider;
import com.cdk.modern.renting.oauth2.users.grantpassword.OAuth2GrantPasswordAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenGenerator;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.time.Duration;
import java.util.UUID;

@Configuration(proxyBeanMethods = false)
public class AuthorizationServerConfiguration {
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain authorizationSecurityFilterChain(
        HttpSecurity http,
        GrantPasswordAuthenticationProvider grantPasswordAuthenticationProvider,
        DaoAuthenticationProvider daoAuthenticationProvider
    ) throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .tokenEndpoint(tokenEndpoint ->
                tokenEndpoint
                    .accessTokenRequestConverter(new OAuth2GrantPasswordAuthenticationConverter())
                    .authenticationProvider(grantPasswordAuthenticationProvider)
                    .authenticationProvider(daoAuthenticationProvider)
            );

        http
            .exceptionHandling(
                exceptions ->
                    exceptions.authenticationEntryPoint(
                        new LoginUrlAuthenticationEntryPoint("/login")
                    )
            );

        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(
        PasswordEncoder passwordEncoder, UserDetailsService userDetailsService
    ) {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public GrantPasswordAuthenticationProvider grantPasswordAuthenticationProvider(
        UserDetailsService userDetailsService, OAuth2TokenGenerator<?> jwtTokenCustomizer,
        OAuth2AuthorizationService authorizationService, PasswordEncoder passwordEncoder
    ) {
        return new GrantPasswordAuthenticationProvider(
            authorizationService, jwtTokenCustomizer, userDetailsService, passwordEncoder
        );
    }

    @Bean
    public OAuth2AuthorizationService authorizationService() {
        return new InMemoryOAuth2AuthorizationService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository(OAuth2Properties oAuth2Properties) {
        RegisteredClient modernRentingClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientName("Modern Renting client")
            .clientId(oAuth2Properties.getClientId())

            // {noop} means "no operation," i.e., a raw password without any encoding applied.
            .clientSecret(oAuth2Properties.getClientSecret())

            .redirectUri("http://localhost:8080/")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantTypePassword.GRANT_PASSWORD)
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                    .accessTokenTimeToLive(Duration.ofMinutes(300))
                    .refreshTokenTimeToLive(Duration.ofMinutes(600))
                    .authorizationCodeTimeToLive(Duration.ofMinutes(20))
                    .reuseRefreshTokens(false)
                    .build()
            )
            .build();

        RegisteredClient serviceClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientName("Modern Renting client")
            .clientId(oAuth2Properties.getSvcClientId())

            // {noop} means "no operation," i.e., a raw password without any encoding applied.
            .clientSecret(oAuth2Properties.getSvcClientSecret())

            .redirectUri("http://localhost:8080/")
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .authorizationGrantType(AuthorizationGrantTypePassword.GRANT_PASSWORD)
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.REFERENCE)
                    .accessTokenTimeToLive(Duration.ofMinutes(300))
                    .refreshTokenTimeToLive(Duration.ofMinutes(600))
                    .authorizationCodeTimeToLive(Duration.ofMinutes(20))
                    .reuseRefreshTokens(false)
                    .build()
            )
            .build();

        return new InMemoryRegisteredClientRepository(modernRentingClient,serviceClient);
    }
}
