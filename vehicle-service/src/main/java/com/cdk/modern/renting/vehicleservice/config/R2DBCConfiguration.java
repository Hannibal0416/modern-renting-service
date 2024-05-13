package com.cdk.modern.renting.vehicleservice.config;

import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//@EnableR2dbcRepositories
//class R2DBCConfiguration extends AbstractR2dbcConfiguration {
//    @Bean
//    public H2ConnectionFactory connectionFactory() {
//        return new H2ConnectionFactory(
//            H2ConnectionConfiguration.builder()
//                .url("mem:testdb;DB_CLOSE_DELAY=-1;")
//                .username("sa")
//                .build()
//        );
//    }
//}