package com.liam.controller.kaptcha;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import io.swagger.annotations.ApiOperation;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author XiaoYang
 * @date 2021-06-05 23:07
 * @projectName cloud-e-office-back-end
 * @name KaptchaController
 * @description
 */
@RestController
public class KaptchaController {

  @Autowired DefaultKaptcha kaptcha;

  @ApiOperation(value = "验证码")
  @GetMapping(value = "/kaptcha", produces = "image/jpeg")
  public void kaptcha(HttpServletRequest request, HttpServletResponse response) {
    //    定义response输出类型为image/jpeg类型
    response.setDateHeader("Expires", 0);
    //    Set standard HTTP/1.1 no-cache headers.
    response.setHeader("Cache-control", "no-store,no-cache,must-revalidate");
    //    Set IE extended HTTP/1.1 no-cache,headers(use addHeader).
    response.addHeader("Cache-control", "post-check=0,pre-check=0");
    //    Set standard HTTP/1.0 no-cache header.
    response.setHeader("Pragma", "no-cache");
    //    return a jpeg
    response.setContentType("image/jpeg");
    //    ------------------生成验证码 begin------------------
    //    获取验证码文本内容
    String text = kaptcha.createText();
    System.out.println("验证码内容:" + text);
    //    将验证码文本内容放入session
    request.getSession().setAttribute("kaptcha", text);
    //    根据文本验证码内容创建图像验证码
    BufferedImage image = kaptcha.createImage(text);
    ServletOutputStream outputStream = null;
    try {
      outputStream = response.getOutputStream();
      //      输出流,输出图片,格式为jpg
      ImageIO.write(image, "jpg", outputStream);
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (null != outputStream) {
        try {
          outputStream.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    //    ------------------生成验证码 end------------------
  }
}
