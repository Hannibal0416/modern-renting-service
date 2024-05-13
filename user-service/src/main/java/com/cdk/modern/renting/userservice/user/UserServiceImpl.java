package com.cdk.modern.renting.userservice.user;

import com.cdk.modern.renting.userservice.config.OAuth2Properties;
import com.cdk.modern.renting.userservice.user.request.UserUpdate;
import com.cdk.modern.renting.userservice.user.response.TokenResponse;
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceImpl implements UserService{

  private final RestTemplate restTemplate;
  private final OAuth2Properties oAuth2Properties;

  @Override
  public UserInfoResponse updateUser(UserUpdate userUpdate) {
    return null;
  }

  @Override
  public TokenResponse refresh(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
//    map.add("grant_type", "grant_password");
    map.add("grant_type", "refresh_token");
    map.add("refresh_token", token);

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

    ResponseEntity<TokenResponse> response = restTemplate.postForEntity( oAuth2Properties.getTokenUri(), request , TokenResponse.class );
    return response.getBody();
  }

  @Override
  public void revoke(String token) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
    map.add("token", token);
    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
    restTemplate.postForEntity( oAuth2Properties.getRevoke(), request , Void.class );
  }
}
