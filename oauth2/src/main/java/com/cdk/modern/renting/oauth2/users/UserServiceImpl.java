package com.cdk.modern.renting.oauth2.users;

import com.cdk.modern.renting.oauth2.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService {
	private final UserRepository repository;

	@Override
	public User getByUsername(String username) {
		if (!StringUtils.hasText(username)) {
			return null;
		}

		return repository.findByUsername(username).orElse(null);
	}

	@Override
	public User save(User entity) {
		return repository.save(entity);
	}
}
