package site.opencs.plotmax.hrm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.opencs.plotmax.hrm.dto.request.SalaryCalculateRequest;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.dto.response.SalaryResponse;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import site.opencs.plotmax.hrm.service.ISalaryService;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 薪资记录表 前端控制器
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@RestController
@RequestMapping("/api/salary")
@RequiredArgsConstructor
public class SalaryController {

    private final ISalaryService salaryService;
    private final IEmployeeService employeeService;

    @PostMapping("/calculate")
    public ApiResponse<SalaryResponse> calculateSalary(
            @Valid @RequestBody SalaryCalculateRequest request) {
        SalaryResponse response = salaryService.calculateSalary(request);
        return ApiResponse.success(response);
    }

    @GetMapping("/slip")
    public ApiResponse<SalaryResponse> getSalarySlip(
            @RequestParam Long employeeId,
            @RequestParam String yearMonth) {
        SalaryResponse response = salaryService.getSalarySlip(employeeId, yearMonth);
        return ApiResponse.success(response);
    }

    @PostMapping("/sign")
    public ApiResponse<?> signSalarySlip(@RequestParam Long salaryId) {
        Long currentUserId = employeeService.getCurrentId();
        boolean success = salaryService.signSalarySlip(salaryId, currentUserId);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR, "签收失败");
    }

    @PostMapping("/calculate-department")
    public ApiResponse<List<SalaryResponse>> calculateDepartmentSalary(
            @RequestParam Long departmentId,
            @RequestParam String yearMonth) {
        List<SalaryResponse> responses = salaryService.calculateDepartmentSalary(departmentId, yearMonth);
        return ApiResponse.success(responses);
    }
}
