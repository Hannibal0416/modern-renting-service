package com.cdk.modern.renting.oauth2.users;


import com.cdk.modern.renting.oauth2.domain.User;

public interface UserService {
	User getByUsername(String username);

    User save(User user);
}