package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.response.BrandResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repository;

    @Override
    public Flux<BrandResponse> getBrands() {
        return repository.findAll().flatMap(
                each -> {
                    BrandResponse response = new BrandResponse();
                    BeanUtils.copyProperties(each, response);
                    return Mono.just(response);
                }
        );
    }
}
