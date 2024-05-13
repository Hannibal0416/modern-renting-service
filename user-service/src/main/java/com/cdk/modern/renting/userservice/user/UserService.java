package com.cdk.modern.renting.userservice.user;



import com.cdk.modern.renting.userservice.user.request.UserCreateRequest;
import com.cdk.modern.renting.userservice.user.request.UserUpdateRequest;
import com.cdk.modern.renting.userservice.user.response.UserInfoResponse;

public interface UserService {
  UserInfoResponse getUser();
  UserInfoResponse createUser(UserCreateRequest userCreateRequest);
  UserInfoResponse updateUser(UserUpdateRequest userUpdate);
}
