package com.cdk.modern.renting.userservice.user;


import com.cdk.modern.renting.userservice.domain.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {
	Role findByName(String name);
}