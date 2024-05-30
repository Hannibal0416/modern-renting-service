package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.domain.Brand;
import com.cdk.modern.renting.vehicleservice.domain.Type;
import com.cdk.modern.renting.vehicleservice.metadata.response.TypeBrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class TypeBrandServiceImpl implements TypeBrandService{
    private final TypeService typeService;
    private final BrandService brandService;

    @Override
    public Mono<TypeBrandResponse> getTypeBrand() {
        return typeService.getTypes().collectList()
                .zipWith(brandService.getBrands().collectList())
                .map(tuple -> {
                    TypeBrandResponse response = new TypeBrandResponse();
                    response.setType(tuple.getT1());
                    response.setBrand(tuple.getT2());
                    return response;
                });
    }
}
