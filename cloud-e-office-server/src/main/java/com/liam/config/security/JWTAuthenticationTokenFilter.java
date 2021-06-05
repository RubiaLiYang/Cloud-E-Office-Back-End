package com.liam.config.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * @author XiaoYang
 * @date 2021-05-28 14:49
 * @projectName cloud-e-office-back-end
 * @name JWTAuthenticationTokenFilter
 * @description JWT授权过滤器
 */
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

//  请求头
  @Value("${jwt.tokenHeader}")
  private String tokenHeader;
//  负载头
  @Value("${jwt.tokenHead}")
  private String tokenHead;

  @Autowired private JWTTokenUtil tokenUtil;

  @Autowired private UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      FilterChain filterChain)
      throws ServletException, IOException {

//    拿到请求头
    String authHeader = httpServletRequest.getHeader(tokenHeader);
//    是否存在token,负载开头是否以bearer开头
    if (null != authHeader && authHeader.startsWith(tokenHead)) {
      String authToken = authHeader.substring(tokenHead.length());
      String username = tokenUtil.getUsernameFromToken(authToken);
      // token 存在用户名,但是未登录
      if (null != username && null == SecurityContextHolder.getContext().getAuthentication()) {
        // 登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        // 判断token是否有效,如果有效,重新放置到用户对象里
        if (tokenUtil.validateToken(authToken, userDetails)) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
          authenticationToken.setDetails(
              new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }
    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}
