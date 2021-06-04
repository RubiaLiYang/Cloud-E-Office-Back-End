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

  /** 注册UserDetailsService Bean */
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

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JWTAuthenticationTokenFilter authenticationTokenFilter() {
    return new JWTAuthenticationTokenFilter();
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
   * @description :放行路径
   */
  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/login", "/logout");
    /** 资源放行 */
    web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "favicon.ico");
    /** swagger资源放行 */
    web.ignoring()
        .antMatchers("/doc.html", "/webjars/**", "/swagger-resources/**", "/v2/api-docs/**");
  }

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
        // 除了上面,所有请求都需要认证
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
