package com.cdk.modern.renting.oauth2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {

    private String clientId;

    private String clientSecret;
  
} 