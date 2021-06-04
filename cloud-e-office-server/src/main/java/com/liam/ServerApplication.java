package com.liam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description server主启动类
 * @Auther XiaoYang
 * @Date 2021-05-01 13:58
 * @ProjectName cloud-e-office-back-end
 * @Name Cloud_E_Office_Server
 */
@SpringBootApplication
@MapperScan("com.liam.mapper")
public class ServerApplication {
  public static void main(String[] args) {
    SpringApplication.run(ServerApplication.class, args); //项目主启动类
  }
}
