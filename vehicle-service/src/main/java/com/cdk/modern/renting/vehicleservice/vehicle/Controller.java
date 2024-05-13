package com.cdk.modern.renting.vehicleservice.vehicle;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("car")
public class Controller {

  @GetMapping
  public String hello() {
    return "hello";
  }
}
