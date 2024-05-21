package com.cdk.modern.renting.userservice.util;

public class PasswordUtils {
  public static String prependNoop(String rawPassword) {
    return "{noop}" + rawPassword;
  }
}
