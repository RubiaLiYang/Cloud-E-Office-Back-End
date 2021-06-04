package com.liam.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * @author XiaoYang
 * @date 2021-05-26 22:50
 * @projectName cloud-e-office-back-end
 * @name JWTTokenUtil
 * @description jwt工具类
 */
@Component
public class JWTTokenUtil {
  private static final String CLAIM_KEY_USERNAME = "sub";
  private static final String CLAIM_KEY_CREATED = "created";

  /** 密钥 */
  @Value("${jwt.secret}")
  private String secret;

  /** 过期时间 */
  @Value("${jwt.expiration}")
  private Long expiration;

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [userDetails]
   * @return java.lang.String
   * @description 根据用户信息生成token
   */
  public String generateToken(UserDetails userDetails) {
//    设置负载信息
    Map<String, Object> claims = new HashMap<>();
//    设置jwt相关用户
    claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
//    设置签发时间
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [claims]
   * @return java.lang.String
   * @description 根据荷载生成 JWT Token
   */
  private String generateToken(Map<String, Object> claims) {
    return Jwts.builder()
//        生成头部信息,以及负载信息
        .setClaims(claims)
//        设置失效时间
        .setExpiration(generateExpirationDate())
//        设置密钥以及加密算法
        .signWith(SignatureAlgorithm.HS512, secret)
//        返回jwt token
        .compact();
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param []
   * @return java.util.Date
   * @description 生成Token 失效时间
   */
  private Date generateExpirationDate() {
    return new Date(System.currentTimeMillis() + expiration * 1000);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token]
   * @return java.lang.String
   * @description : 从token中获取登录用户名
   */
  public String getUsernameFromToken(String token) {
    String username;
    try {
      Claims claims = getClaimsFormToken(token);
      username = claims.getSubject();
//      如果有异常就设置username为空
    } catch (Exception e) {
      username = null;
    }
    return username;
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token]
   * @return io.jsonwebtoken.Claims
   * @description 从Token中获取荷载
   */
  private Claims getClaimsFormToken(String token) {
    Claims claims = null;
    try {
//      拿到荷载信息
      claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return claims;
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token, userDetails]
   * @return  判断用户名是否是当前用户,以及判断token是否过期
   * @description 验证Token是否有效
   */
  public boolean validateToken(String token, UserDetails userDetails) {
    String username = getUsernameFromToken(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token]
   * @return 如果失效时间小于当前时间,说明token已经过期(true),如果失效时间大于当前时间,说明未过期(false)
   * @description 判断token是否失效
   */
  private boolean isTokenExpired(String token) {
    Date expireDate = getExpiredDateFromToken(token);
    return expireDate.before(new Date());
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token]
   * @return java.util.Date
   * @description 从token中获取过期时间
   */
  private Date getExpiredDateFromToken(String token) {
    Claims claims = getClaimsFormToken(token);
    return claims.getExpiration();
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token]
   * @return 如果token已经过期,则不可以被刷新(false),如果未过期,则可以刷新(true)
   * @description :判断token是否可以被刷新
   */
  public boolean canRefresh(String token) {

    return !isTokenExpired(token);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/26
   * @param [token]
   * @return java.lang.String
   * @description :刷新token
   */
  public String refreshToken(String token) {
    Claims claims = getClaimsFormToken(token);
//    重新定义创建时间
    claims.put(CLAIM_KEY_CREATED, new Date());
    return generateToken(claims);
  }
}
