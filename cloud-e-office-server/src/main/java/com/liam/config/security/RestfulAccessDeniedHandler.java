package com.liam.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liam.pojo.ResponseBean;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * @author XiaoYang
 * @date 2021-05-28 15:40
 * @projectName cloud-e-office-back-end
 * @name RestfulAccessDeniedHandler
 * @description :当访问接口没有权限时,自定义返回结果
 */
@Component
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {
    httpServletResponse.setCharacterEncoding("UTF-8");
    httpServletResponse.setContentType("application/json");
    PrintWriter out = httpServletResponse.getWriter();
    ResponseBean bean = ResponseBean.error("权限不足,请联系管理员!");
    bean.setCode(403);
    out.write(new ObjectMapper().writeValueAsString(bean));
    out.flush();
    out.close();
  }
}
