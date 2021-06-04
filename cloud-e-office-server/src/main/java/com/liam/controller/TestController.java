package com.liam.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoYang
 * @date 2021-05-31 10:31
 * @projectName cloud-e-office-back-end
 * @name TestController
 * @description :测试Controller
 */
@RestController
public class TestController  {

  @GetMapping("hello")
  public String test() {
    return "hello";
  }
}
