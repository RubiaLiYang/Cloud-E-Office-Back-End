package com.liam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liam.mapper.PositionMapper;
import com.liam.pojo.Position;
import com.liam.service.IPositionService;
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
public class PositionServiceImpl extends ServiceImpl<PositionMapper, Position> implements IPositionService {

}
