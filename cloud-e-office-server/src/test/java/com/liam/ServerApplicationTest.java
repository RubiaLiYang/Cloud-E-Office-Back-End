package com.liam;

import java.util.Date;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author XiaoYang
 * @date 2021-06-02 22:55
 * @projectName cloud-e-office-back-end
 * @name ServerApplicationTest
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerApplicationTest {

  /** 过期时间 */
  @Value("${jwt.expiration}")
  private Long expiration;

  @Test
  public void testBeforeDate() {
    Date oldDate = new Date();
    Date newDate = new Date(System.currentTimeMillis() + expiration * 1000);
    System.out.println(oldDate.before(newDate));
  }
}
