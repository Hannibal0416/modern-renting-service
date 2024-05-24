package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends R2dbcRepository<Brand, Integer> {

}
