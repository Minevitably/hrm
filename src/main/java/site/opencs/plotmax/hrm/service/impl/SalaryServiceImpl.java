package site.opencs.plotmax.hrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;
import site.opencs.plotmax.hrm.dto.request.SalaryCalculateRequest;
import site.opencs.plotmax.hrm.dto.response.AttendanceResponse;
import site.opencs.plotmax.hrm.dto.response.SalaryResponse;
import site.opencs.plotmax.hrm.entity.Attendance;
import site.opencs.plotmax.hrm.entity.Employee;
import site.opencs.plotmax.hrm.entity.Salary;
import site.opencs.plotmax.hrm.exception.BusinessException;
import site.opencs.plotmax.hrm.mapper.SalaryMapper;
import site.opencs.plotmax.hrm.service.IAttendanceService;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import site.opencs.plotmax.hrm.service.ISalaryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 薪资记录表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
@RequiredArgsConstructor
public class SalaryServiceImpl extends ServiceImpl<SalaryMapper, Salary> implements ISalaryService {

    private final SalaryMapper salaryMapper;
    private final IEmployeeService employeeService;
    private final IAttendanceService attendanceService;

    // 系统配置参数
    private static final BigDecimal PERFORMANCE_BASE_RATE = new BigDecimal("0.2"); // 绩效基数比例
    private static final BigDecimal FULL_ATTENDANCE_BONUS = new BigDecimal("500"); // 全勤奖金额
    private static final BigDecimal INSURANCE_RATE = new BigDecimal("0.175");      // 社保公积金比例
    private static final BigDecimal TAX_THRESHOLD = new BigDecimal("5000");        // 个税起征点

    @Override
    @Transactional
    public SalaryResponse calculateSalary(SalaryCalculateRequest request) {
        // 检查是否已存在工资记录
        if (!Boolean.TRUE.equals(request.getRecalculate())) {
            SalaryResponse existing = salaryMapper.selectSalaryDetail(
                    request.getEmployeeId(), request.getYearMonth());
            if (existing != null) {
                return existing;
            }
        }

        // 获取员工基本信息
        Employee employee = employeeService.getById(request.getEmployeeId());
        if (employee == null) {
            throw new BusinessException("员工不存在");
        }

        // 计算各组成部分
        Salary salary = new Salary();
        salary.setEmployeeId(request.getEmployeeId());
        salary.setYearMonth(request.getYearMonth());

        // 1. 基本工资
        salary.setBaseSalary(employee.getBaseSalary());

        // 2. 绩效工资 = 基本工资 × 0.2 × 绩效系数
        BigDecimal performanceSalary = employee.getBaseSalary()
                .multiply(PERFORMANCE_BASE_RATE)
                .multiply(employee.getPerformanceCoefficient());
        salary.setPerformanceSalary(performanceSalary);

        // 3. 全勤奖(需检查当月考勤)
        boolean fullAttendance = checkFullAttendance(request.getEmployeeId(), request.getYearMonth());
        salary.setFullAttendanceBonus(fullAttendance ? FULL_ATTENDANCE_BONUS : BigDecimal.ZERO);

        // 4. 考勤扣款
        BigDecimal attendanceDeduction = calculateAttendanceDeduction(request.getEmployeeId(), request.getYearMonth());
        salary.setAttendanceDeduction(attendanceDeduction);

        // 5. 社保公积金 = 基本工资 × 比例
        BigDecimal insuranceDeduction = employee.getBaseSalary().multiply(INSURANCE_RATE);
        salary.setInsuranceDeduction(insuranceDeduction);

        // 6. 计算税前工资
        BigDecimal grossSalary = employee.getBaseSalary()
                .add(performanceSalary)
                .add(salary.getFullAttendanceBonus())
                .subtract(attendanceDeduction);

        // 7. 个人所得税(简化计算)
        BigDecimal taxableIncome = grossSalary.subtract(insuranceDeduction).subtract(TAX_THRESHOLD);
        BigDecimal taxDeduction = taxableIncome.compareTo(BigDecimal.ZERO) > 0 ?
                taxableIncome.multiply(new BigDecimal("0.03")) : BigDecimal.ZERO;
        salary.setTaxDeduction(taxDeduction);

        // 8. 计算实发工资
        BigDecimal netSalary = grossSalary.subtract(insuranceDeduction).subtract(taxDeduction);
        salary.setNetSalary(netSalary);

        // 保存或更新工资记录
        LambdaQueryWrapper<Salary> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Salary::getEmployeeId, request.getEmployeeId())
                .eq(Salary::getYearMonth, request.getYearMonth());

        if (this.count(queryWrapper) > 0) {
            this.update(salary, queryWrapper);
        } else {
            salary.setCreateTime(LocalDateTime.now());
            salary.setUpdateTime(LocalDateTime.now());
            this.save(salary);
        }

        return convertToResponse(this.getOne(queryWrapper));
    }

    @Override
    public SalaryResponse getSalarySlip(Long employeeId, String yearMonth) {
        SalaryResponse response = salaryMapper.selectSalaryDetail(employeeId, yearMonth);
        if (response == null) {
            throw new BusinessException("工资条不存在");
        }
        return response;
    }

    @Override
    @Transactional
    public boolean signSalarySlip(Long salaryId, Long employeeId) {
        Salary salary = this.getById(salaryId);
        if (salary == null || !salary.getEmployeeId().equals(employeeId)) {
            throw new BusinessException("工资条不存在或不属于当前员工");
        }

        salary.setIsSigned((byte) 1);
        salary.setSignTime(LocalDateTime.now());
        return this.updateById(salary);
    }

    @Override
    @Transactional
    public List<SalaryResponse> calculateDepartmentSalary(Long departmentId, String yearMonth) {
        // 获取部门所有员工
        List<Employee> employees = employeeService.list(
                new LambdaQueryWrapper<Employee>().eq(Employee::getDepartmentId, departmentId));

        return employees.stream()
                .map(employee -> {
                    SalaryCalculateRequest request = new SalaryCalculateRequest();
                    request.setEmployeeId(employee.getId());
                    request.setYearMonth(yearMonth);
                    return calculateSalary(request);
                })
                .collect(Collectors.toList());
    }

    private boolean checkFullAttendance(Long employeeId, String yearMonth) {
        // 查询当月所有考勤记录
        Page<AttendanceResponse> attendances = attendanceService.getAttendanceRecords(employeeId, yearMonth, 1, 31);
        return attendances.getRecords().stream()
                .noneMatch(a -> "ABSENCE".equals(a.getStatus()) ||
                        "LATE".equals(a.getStatus()) ||
                        "EARLY_LEAVE".equals(a.getStatus()));
    }

    private BigDecimal calculateAttendanceDeduction(Long employeeId, String yearMonth) {
        // 查询当月所有考勤记录
        Page<AttendanceResponse> attendances = attendanceService.getAttendanceRecords(employeeId, yearMonth, 1, 31);

        BigDecimal totalDeduction = BigDecimal.ZERO;
        BigDecimal dailySalary = employeeService.getById(employeeId).getBaseSalary()
                .divide(new BigDecimal("21.75"), 2, RoundingMode.HALF_UP);

        for (AttendanceResponse att : attendances.getRecords()) {
            if ("ABSENCE".equals(att.getStatus())) {
                // 旷工扣款: 日薪×3
                totalDeduction = totalDeduction.add(dailySalary.multiply(new BigDecimal("3")));
            } else if ("LATE".equals(att.getStatus())) {
                // 迟到扣款: 每分钟1元
                totalDeduction = totalDeduction.add(
                        new BigDecimal(att.getLateMinutes()));
            } else if ("EARLY_LEAVE".equals(att.getStatus())) {
                // 早退扣款: 每分钟2元
                totalDeduction = totalDeduction.add(
                        new BigDecimal(att.getEarlyLeaveMinutes()).multiply(new BigDecimal("2")));
            }
        }

        return totalDeduction;
    }

    private SalaryResponse convertToResponse(Salary salary) {
        SalaryResponse response = new SalaryResponse();
        BeanUtils.copyProperties(salary, response);

        // 格式化金额
        response.setBaseSalary(salary.getBaseSalary().toString());
        response.setPerformanceSalary(salary.getPerformanceSalary().toString());
        response.setFullAttendanceBonus(salary.getFullAttendanceBonus().toString());
        response.setAttendanceDeduction(salary.getAttendanceDeduction().toString());
        response.setInsuranceDeduction(salary.getInsuranceDeduction().toString());
        response.setTaxDeduction(salary.getTaxDeduction().toString());

        // 计算合计
        BigDecimal totalIncome = salary.getBaseSalary()
                .add(salary.getPerformanceSalary())
                .add(salary.getFullAttendanceBonus());

        BigDecimal totalDeduction = salary.getAttendanceDeduction()
                .add(salary.getInsuranceDeduction())
                .add(salary.getTaxDeduction());

        response.setTotalIncome(totalIncome.toString());
        response.setTotalDeduction(totalDeduction.toString());
        response.setNetSalary(salary.getNetSalary().toString());

        // 转换签收状态
        response.setIsSigned(salary.getIsSigned() == 1);

        return response;
    }
}
