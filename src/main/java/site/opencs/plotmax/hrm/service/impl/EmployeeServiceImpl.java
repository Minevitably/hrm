package site.opencs.plotmax.hrm.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import site.opencs.plotmax.hrm.dto.request.EmployeeRequest;
import site.opencs.plotmax.hrm.dto.response.EmployeeResponse;
import site.opencs.plotmax.hrm.entity.Employee;
import site.opencs.plotmax.hrm.mapper.EmployeeMapper;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
