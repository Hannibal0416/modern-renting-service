package com.cdk.modern.renting.oauth2.users;


import com.cdk.modern.renting.oauth2.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    if (!StringUtils.hasText(username)) {
      return null;
    }
    User user = userRepository.findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException("Unable to found user: " + username));

    return new CustomUserDetails(user);
  }
}
