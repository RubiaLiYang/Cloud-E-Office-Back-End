package com.liam.config.security;

import com.liam.pojo.Admin;
import com.liam.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author XiaoYang
 * @date 2021-05-28 14:12
 * @projectName cloud-e-office-back-end
 * @name SecurityConfig
 * @description
 */
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  /** 注入用户service */
  @Autowired private IAdminService adminService;

  /** 当未登录或者token失效时,自定义返回的结果 */
  @Autowired private RestAuthenticationEntryPoint authenticationEntryPoint;
  /** 当访问接口没有权限时,自定义返回结果 */
  @Autowired private RestfulAccessDeniedHandler accessDeniedHandler;

  /**
   * @author XiaoYang
   * @date 2021/6/5
   * @param []
   * @return org.springframework.security.crypto.password.PasswordEncoder
   * @description :设置用户密码
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWTAuthenticationTokenFilter authenticationTokenFilter() {
    return new JWTAuthenticationTokenFilter();
  }

  /**
   * @author      XiaoYang
   * @date        2021/6/5
   * @param       []
   * @return      org.springframework.security.core.userdetails.UserDetailsService
   * @description :创建自定义UserDetails实现类
   */
  @Bean
  public UserDetailsService userDetailsService() {
    return username -> {
      Admin admin = adminService.getAdminByUserName(username);
      if (null != admin) {
        return admin;
      }
      return null;
    };
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
  }

  /**
   * @author XiaoYang
   * @date 2021/5/31
   * @param [web]
   * @return void
   * @description security放行路径
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
//    自定义放行路径
    web.ignoring().antMatchers("/login", "/logout","/kaptcha");
//    资源放行
    web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "favicon.ico");
//    swagger资源放行
    web.ignoring()
        .antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**");
  }

  /**
   * @author XiaoYang
   * @date 2021/6/5
   * @param [http]
   * @return void
   * @description :security配置信息
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // 使用jwt,不需要csrf
    http.csrf()
        .disable()
        // 基于token,不需要session
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    // 允许登录访问
    http.authorizeRequests()
        //        拦截所有请求
        .anyRequest()
        .authenticated();
    // 缓存关闭
    http.headers().cacheControl();

    // 添加jwt登录授权拦截器
    http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    // 添加自定义未授权和未登录的结果返回
    http.exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler)
        .authenticationEntryPoint(authenticationEntryPoint);
  }
}
