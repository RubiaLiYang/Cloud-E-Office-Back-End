package com.liam.controller.login;

import com.liam.pojo.Admin;
import com.liam.pojo.AdminLoginParam;
import com.liam.pojo.ResponseBean;
import com.liam.service.IAdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.security.Principal;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoYang
 * @date 2021-05-28 11:24
 * @projectName cloud-e-office-back-end
 * @name LoginController
 * @description 登录Controller
 */
@Api(tags = "LoginController")
@RestController
public class LoginController {


  @Autowired private IAdminService adminService;

  /**
   * @author      XiaoYang
   * @date        2021/5/28
   * @param       [adminLoginParam, request]
   * @return      com.liam.pojo.ResponseBean
   * @description :登录成功后拿到token
   */
  @ApiOperation(value = "登录之后返回Token")
  @PostMapping("/login")
  public ResponseBean login(@RequestBody AdminLoginParam adminLoginParam, HttpServletRequest request) {
    return adminService.login(
        adminLoginParam.getUsername(), adminLoginParam.getPassword(),adminLoginParam.getVerificationCode(), request);
  }

  /**
   * @author      XiaoYang
   * @date        2021/5/28
   * @param       [principal]
   * @return      com.liam.pojo.Admin
   * @description :获取当前用户的登录信息并且屏蔽掉密码
   */
  @ApiOperation(value = "获取当前登录用户的信息")
  @GetMapping("/admin/info")
  public Admin getAdminInfo(Principal principal) {
    if (null == principal) {
      return null;
    }
//    获取当前登录用户的用户名
    String username = principal.getName();
//    根据当前登录的用户名,获取当前用户对象
    Admin admin=adminService.getAdminByUserName(username);
//    出于保密目的,密码返回为空
    admin.setPassword(null);
    return admin;
  }

  /**
   * @author      XiaoYang
   * @date        2021/5/28
   * @param       []
   * @return      com.liam.pojo.ResponseBean
   * @description :退出登录
   */
  @ApiOperation(value = "退出登录")
  @PostMapping("/logout")
  public ResponseBean logout() {
    return ResponseBean.success("注销成功!");
  }

}
