package com.cdk.modern.renting.oauth2.users;


import com.cdk.modern.renting.oauth2.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

@Query("""
      SELECT u
      FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.authorities
      WHERE u.username = :username
      """)
	Optional<User> findByUsername(String username);
}