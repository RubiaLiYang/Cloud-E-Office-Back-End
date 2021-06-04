package com.liam.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liam.pojo.ResponseBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * @author XiaoYang
 * @date 2021-05-28 15:33
 * @projectName cloud-e-office-back-end
 * @name RestAuthenticationEntryPoint
 * @description :当未登录或者token失效时,自定义返回的结果
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json");
    PrintWriter out = httpServletResponse.getWriter();
    ResponseBean bean = ResponseBean.error("尚未登录,请登录!");
    bean.setCode(401);
    out.write(new ObjectMapper().writeValueAsString(bean));
    out.flush();
    out.close();
  }
}
