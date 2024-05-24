package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.domain.Type;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends R2dbcRepository<Type, Integer> {}

