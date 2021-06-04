package com.liam.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liam.pojo.Admin;
import com.liam.pojo.ResponseBean;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Liam
 * @since 2021-05-03
 */
public interface IAdminService extends IService<Admin> {

  /**
   * @author      XiaoYang
   * @date        2021/5/28
   * @param       [username, password, request]
   * @return      com.liam.pojo.ResponseBean
   * @description :登录之后返回token
   */
  ResponseBean login(String username, String password, HttpServletRequest request);

  /**
   * @author      XiaoYang
   * @date        2021/5/28
   * @param       [username]
   * @return      com.liam.pojo.Admin
   * @description :根据用户名获取用户
   */
  Admin getAdminByUserName(String username);
}
