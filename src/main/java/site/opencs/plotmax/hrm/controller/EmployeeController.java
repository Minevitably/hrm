package site.opencs.plotmax.hrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import site.opencs.plotmax.hrm.dto.request.EmployeeRequest;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.dto.response.EmployeeResponse;
import site.opencs.plotmax.hrm.entity.Employee;
import site.opencs.plotmax.hrm.entity.User;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import site.opencs.plotmax.hrm.service.IEmployeeService;
import site.opencs.plotmax.hrm.service.IUserService;
import site.opencs.plotmax.hrm.util.SecurityUtil;

/**
 * <p>
 * 员工表 前端控制器
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;
    @Autowired
    private IUserService userService;

    @GetMapping("/list")
    public ApiResponse<Page<EmployeeResponse>> listEmployees(
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String group,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<EmployeeResponse> pageData = employeeService.listEmployees(department, group, page, size);
        return ApiResponse.success(pageData);
    }

    @GetMapping("/{id}")
    public ApiResponse<EmployeeResponse> getEmployee(@PathVariable Long id) {
        EmployeeResponse employee = employeeService.getEmployeeDetail(id);
        return ApiResponse.success(employee);
    }

    @PutMapping("/self-update")
    public ApiResponse<?> selfUpdate(@RequestBody EmployeeRequest request) {
        // 从安全上下文中获取当前用户ID
        String username = SecurityUtil.getCurrentUsername().orElse(null); // 如果Optional为空，则返回null
        if (username == null) {
            // 处理用户未登录或无法获取用户名的情况
            return ApiResponse.error(ErrorCode.UNAUTHORIZED, "用户未登录或无法获取用户名");
        }
        User user = userService.selectByUsername(username);
        Long currentUserId = user.getEmployeeId();
        if (currentUserId == null) {
            // 处理用户未与员工绑定的情况
            return ApiResponse.error(ErrorCode.UNAUTHORIZED, "用户未与员工绑定");
        }
        Long currentEmployeeId = user.getEmployeeId();
        boolean success = employeeService.selfUpdate(currentEmployeeId, request);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR,"更新失败");
    }

    @PostMapping("/upload-document")
    public ApiResponse<?> uploadDocument(
            @RequestParam Long employeeId,
            @RequestParam String documentType,
            @RequestParam MultipartFile file) {
        // 简单验证文件类型
        if (!"contract".equalsIgnoreCase(documentType) && !"idCard".equalsIgnoreCase(documentType)) {
            return ApiResponse.error(ErrorCode.SYSTEM_ERROR,"不支持的文件类型");
        }

        // 验证文件大小
        if (file.getSize() > 5 * 1024 * 1024) { // 5MB
            return ApiResponse.error(ErrorCode.SYSTEM_ERROR,"文件大小不能超过5MB");
        }

        String filePath = employeeService.uploadDocument(employeeId, documentType, file);
        return ApiResponse.success(filePath);
    }
}
