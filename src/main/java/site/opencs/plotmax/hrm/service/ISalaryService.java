package site.opencs.plotmax.hrm.service;

import site.opencs.plotmax.hrm.dto.request.SalaryCalculateRequest;
import site.opencs.plotmax.hrm.dto.response.SalaryResponse;
import site.opencs.plotmax.hrm.entity.Salary;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 薪资记录表 服务类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface ISalaryService extends IService<Salary> {

    /**
     * 计算工资
     */
    SalaryResponse calculateSalary(SalaryCalculateRequest request);

    /**
     * 获取工资条
     */
    SalaryResponse getSalarySlip(Long employeeId, String yearMonth);

    /**
     * 电子签收
     */
    boolean signSalarySlip(Long salaryId, Long employeeId);

    /**
     * 批量计算部门工资
     */
    List<SalaryResponse> calculateDepartmentSalary(Long departmentId, String yearMonth);
}
