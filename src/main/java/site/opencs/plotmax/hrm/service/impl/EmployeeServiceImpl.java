package site.opencs.plotmax.hrm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.opencs.plotmax.hrm.dto.request.EmployeeRequest;
import site.opencs.plotmax.hrm.dto.response.EmployeeResponse;
import site.opencs.plotmax.hrm.entity.Employee;
import site.opencs.plotmax.hrm.entity.User;
import site.opencs.plotmax.hrm.exception.AuthException;
import site.opencs.plotmax.hrm.exception.BusinessException;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import site.opencs.plotmax.hrm.mapper.EmployeeMapper;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import site.opencs.plotmax.hrm.service.IUserService;
import site.opencs.plotmax.hrm.util.SecurityUtil;

import java.time.LocalDate;

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

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private IUserService userService;

    @Override
    public Long getCurrentId() {
        // 从安全上下文中获取当前用户ID
        String username = SecurityUtil.getCurrentUsername().orElse(null); // 如果Optional为空，则返回null
        if (username == null) {
            // 处理用户未登录或无法获取用户名的情况
            throw new AuthException("用户未登录或无法获取用户名", ErrorCode.UNAUTHORIZED);
        }
        User user = userService.selectByUsername(username);
        Long currentUserId = user.getEmployeeId();
        if (currentUserId == null) {
            // 处理用户未与员工绑定的情况
            throw new BusinessException("用户未与员工绑定");
        }
        return currentUserId;
    }
    @Override
    public Page<EmployeeResponse> listEmployees(String department, String group, int page, int size) {
        Page<EmployeeResponse> pageParam = new Page<>(page, size);
        return employeeMapper.selectEmployeePage(pageParam, department, group);
    }

    @Override
    public EmployeeResponse getEmployeeDetail(Long id) {
        return employeeMapper.selectEmployeeDetail(id);
    }

    @Override
    @Transactional
    public boolean updateEmployeeInfo(EmployeeRequest request) {
        Employee employee = convertToEntity(request);
        return this.updateById(employee);
    }

    @Override
    @Transactional
    public boolean selfUpdate(Long employeeId, EmployeeRequest request) {
        // 自助更新只能更新部分字段
        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setContactPhone(request.getContactPhone());
        employee.setEmergencyContact(request.getEmergencyContact());
        employee.setEmergencyPhone(request.getEmergencyPhone());
        return this.updateById(employee);
    }

    @Override
    public String uploadDocument(Long employeeId, String documentType, MultipartFile file) {
        // 文件上传占位符实现
        String filePath = "placeholder/path/to/" + documentType + "_" + employeeId + ".tmp";

        // 更新员工记录中的文件路径
        Employee employee = new Employee();
        employee.setId(employeeId);
        if ("contract".equalsIgnoreCase(documentType)) {
            employee.setContractFile(filePath);
        } else if ("idCard".equalsIgnoreCase(documentType)) {
            employee.setIdCardFile(filePath);
        }
        this.updateById(employee);

        return filePath;
    }

    private Employee convertToEntity(EmployeeRequest request) {
        Employee employee = new Employee();
        BeanUtils.copyProperties(request, employee);
        employee.setBirthDate(LocalDate.parse(request.getBirthDate()));
        employee.setHireDate(LocalDate.parse(request.getHireDate()));
        return employee;
    }
}
