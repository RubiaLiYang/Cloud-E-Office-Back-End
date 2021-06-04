package com.liam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liam.mapper.MailLogMapper;
import com.liam.pojo.MailLog;
import com.liam.service.IMailLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Liam
 * @since 2021-05-03
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
