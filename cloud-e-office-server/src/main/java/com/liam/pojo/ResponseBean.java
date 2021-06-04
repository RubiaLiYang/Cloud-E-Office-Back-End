package com.liam.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author XiaoYang
 * @date 2021-05-28 9:53
 * @projectName cloud-e-office-back-end
 * @name ResponseBean
 * @description :公共返回对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseBean {
  /** 状态 */
  private long code;
  /** 信息 */
  private String message;
  /** 对象 */
  private Object obj;

  /**
   * @author XiaoYang
   * @date 2021/5/28
   * @param [message]
   * @return com.liam.pojo.ResponseBean
   * @description :成功返回信息
   */
  public static ResponseBean success(String message) {
    return new ResponseBean(200, message, null);
  }
  /**
   * @author XiaoYang
   * @date 2021/5/28
   * @param [message]
   * @return com.liam.pojo.ResponseBean
   * @description :成功返回结果
   */
  public static ResponseBean success(String message, Object obj) {
    return new ResponseBean(200, message, obj);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/28
   * @param [message]
   * @return com.liam.pojo.ResponseBean
   * @description :失败返回信息
   */
  public static ResponseBean error(String message) {
    return new ResponseBean(500, message, null);
  }

  /**
   * @author XiaoYang
   * @date 2021/5/28
   * @param [message]
   * @return com.liam.pojo.ResponseBean
   * @description :失败返回结果
   */
  public static ResponseBean error(String message, Object obj) {
    return new ResponseBean(500, message, obj);
  }
}
