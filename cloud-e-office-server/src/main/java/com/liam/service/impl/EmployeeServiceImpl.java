package com.liam.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liam.mapper.EmployeeMapper;
import com.liam.pojo.Employee;
import com.liam.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
