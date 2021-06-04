package com.liam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liam.mapper.NationMapper;
import com.liam.pojo.Nation;
import com.liam.service.INationService;
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
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}
