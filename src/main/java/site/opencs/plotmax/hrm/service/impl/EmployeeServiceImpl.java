package site.opencs.plotmax.hrm.service.impl;

import site.opencs.plotmax.hrm.entity.Employee;
import site.opencs.plotmax.hrm.mapper.EmployeeMapper;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 员工表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
