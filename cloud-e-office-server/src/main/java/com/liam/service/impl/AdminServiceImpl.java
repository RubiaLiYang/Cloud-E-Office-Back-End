package com.liam.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liam.config.security.JWTTokenUtil;
import com.liam.mapper.AdminMapper;
import com.liam.pojo.Admin;
import com.liam.pojo.ResponseBean;
import com.liam.service.IAdminService;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author      XiaoYang
 * @date        2021/6/4
 * @description :管理员服务类
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

  @Autowired(required = false)
  private AdminMapper adminMapper;

  /** security UserDetailService实现安全授权 */
  @Autowired private UserDetailsService userDetailsService;

  /** security 提高的加密密码工具 */
  @Autowired private PasswordEncoder passwordEncoder;

  /** JWT 工具类 */
  @Autowired private JWTTokenUtil tokenUtil;

  @Value("${jwt.tokenHead}")
  private String tokenHead;

  /**
   * @author XiaoYang
   * @date 2021/5/28
   * @param [username, password, request]
   * @return com.liam.pojo.ResponseBean
   * @description :登录之后返回token
   */
  @Override
  public ResponseBean login(String username, String password, HttpServletRequest request) {

    // 登录
    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
    //    判断用户信息是否正确
    if (null == userDetails || !passwordEncoder.matches(password, userDetails.getPassword())) {
      return ResponseBean.error("用户名或密码不正确");
    }
    //    判断用户是否被禁用
    if (!userDetails.isEnabled()) {
      return ResponseBean.error("账号被禁用,请联系管理员");
    }

    // 更新security登录用户对象
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    //    将当前用户放入到全局对象
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    // 生成token
    String token = tokenUtil.generateToken(userDetails);
    Map<String, String> tokenMap = new HashMap<>();
    tokenMap.put("token", token);
    //    设置token 头部信息
    tokenMap.put("tokenHead", tokenHead);
    return ResponseBean.success("登录成功", tokenMap);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/28
   * @param [username]
   * @return com.liam.pojo.Admin
   * @description :根据用户名获取用户
   */
  @Override
  public Admin getAdminByUserName(String username) {

    //拿到非禁用的用户对象
    return adminMapper.selectOne(
        new QueryWrapper<Admin>().eq("username", username).eq("enabled", true));
  }
}
