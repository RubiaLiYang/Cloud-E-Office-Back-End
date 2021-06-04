package com.liam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liam.mapper.MenuMapper;
import com.liam.pojo.Menu;
import com.liam.service.IMenuService;
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
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

}
