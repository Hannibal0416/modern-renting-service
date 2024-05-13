package com.cdk.modern.renting.userservice.user;


import com.cdk.modern.renting.userservice.user.request.UserUpdate;
import com.cdk.modern.renting.userservice.user.response.TokenResponse;
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;

public interface UserService {

  UserInfoResponse updateUser(UserUpdate userUpdate);
  TokenResponse refresh(String token);
}
