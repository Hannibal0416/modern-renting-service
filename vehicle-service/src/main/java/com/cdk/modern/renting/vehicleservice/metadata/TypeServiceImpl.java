package com.cdk.modern.renting.vehicleservice.metadata;

import com.cdk.modern.renting.vehicleservice.metadata.response.TypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class TypeServiceImpl implements TypeService {
    private final TypeRepository repository;

    @Override
    public Flux<TypeResponse> getTypes() {
        return repository.findAll().flatMap(
                each -> {
                    TypeResponse response = new TypeResponse();
                    BeanUtils.copyProperties(each, response);
                    return Mono.just(response);
                }
        );
    }
}
