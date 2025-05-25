package site.opencs.plotmax.hrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import site.opencs.plotmax.hrm.dto.request.ApprovalRequest;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.dto.response.ApprovalProcessRequest;
import site.opencs.plotmax.hrm.entity.Approval;
import site.opencs.plotmax.hrm.entity.User;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import site.opencs.plotmax.hrm.service.IApprovalService;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import site.opencs.plotmax.hrm.util.SecurityUtil;

import javax.validation.Valid;

/**
 * <p>
 * 审批记录表 前端控制器
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@RestController
@RequestMapping("/api/approval")
@RequiredArgsConstructor
public class ApprovalController {

    private final IApprovalService approvalService;

    private final IEmployeeService employeeService;

    @PostMapping("/apply")
    public ApiResponse<?> apply(@Valid @RequestBody ApprovalRequest request) {
        Long currentUserId = employeeService.getCurrentId();
        boolean success = approvalService.submitApproval(currentUserId, request);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR, "申请提交失败");
    }

    @GetMapping("/list")
    public ApiResponse<Page<Approval>> listApprovals(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Long currentUserId = employeeService.getCurrentId();

        Page<Approval> pageData = approvalService.listApprovals(status, type, currentUserId, page, size);
        return ApiResponse.success(pageData);
    }

    @PostMapping("/process")
    public ApiResponse<?> processApproval(
            @Valid @RequestBody ApprovalProcessRequest request) {
        Long currentUserId = employeeService.getCurrentId();
        boolean success = approvalService.processApproval(currentUserId, request);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR, "审批处理失败");
    }
}
