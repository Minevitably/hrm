package site.opencs.plotmax.hrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import site.opencs.plotmax.hrm.dto.request.EmployeeRequest;
import site.opencs.plotmax.hrm.dto.response.EmployeeResponse;
import site.opencs.plotmax.hrm.entity.Employee;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 员工表 服务类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface IEmployeeService extends IService<Employee> {

    /**
     * 获取当前登录的用户对应的员工id
     * @return
     */
    Long getCurrentId();

    Page<EmployeeResponse> listEmployees(String department, String group, int page, int size);

    EmployeeResponse getEmployeeDetail(Long id);

    boolean updateEmployeeInfo(EmployeeRequest request);

    boolean selfUpdate(Long employeeId, EmployeeRequest request);

    String uploadDocument(Long employeeId, String documentType, MultipartFile file);
}
