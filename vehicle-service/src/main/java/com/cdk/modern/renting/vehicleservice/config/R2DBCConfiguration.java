package com.cdk.modern.renting.vehicleservice.config;

import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

import io.netty.util.internal.StringUtil;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import io.r2dbc.spi.ConnectionFactoryOptions.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
class R2DBCConfiguration {
  @Bean
  public ConnectionFactory connectionFactory(R2DBCConfigurationProperties properties) {
    ConnectionFactoryOptions baseOptions = ConnectionFactoryOptions.parse(properties.getUrl());
    Builder ob = ConnectionFactoryOptions.builder().from(baseOptions);
    if (!StringUtil.isNullOrEmpty(properties.getUsername())) {
      ob = ob.option(ConnectionFactoryOptions.USER, properties.getUsername());
    }
    if (!StringUtil.isNullOrEmpty(properties.getPassword())) {
      ob = ob.option(ConnectionFactoryOptions.PASSWORD, properties.getPassword());
    }
    return ConnectionFactories.get(ob.build());
  }
}

@Data
@Component
@ConfigurationProperties(prefix = "spring.r2dbc")
class R2DBCConfigurationProperties {
  private String url;
  private String username;
  private String password;

}