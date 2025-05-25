package site.opencs.plotmax.hrm.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.dto.response.AttendanceResponse;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import site.opencs.plotmax.hrm.service.IAttendanceService;
import site.opencs.plotmax.hrm.service.IEmployeeService;

/**
 * <p>
 * 考勤记录表 前端控制器
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final IAttendanceService attendanceService;
    private final IEmployeeService employeeService;

    @PostMapping("/check-in")
    public ApiResponse<?> checkIn(@RequestParam(required = false) String ipAddress) {
        Long currentUserId = employeeService.getCurrentId();
        boolean success = attendanceService.processCheckIn(currentUserId, true);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR, "上班打卡失败");
    }

    @PostMapping("/check-out")
    public ApiResponse<?> checkOut(@RequestParam(required = false) String ipAddress) {
        Long currentUserId = employeeService.getCurrentId();
        boolean success = attendanceService.processCheckIn(currentUserId, false);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR, "下班打卡失败");
    }

    @GetMapping("/records")
    public ApiResponse<Page<AttendanceResponse>> getRecords(
            @RequestParam(required = false) Long employeeId,
            @RequestParam(required = false) String month,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AttendanceResponse> records = attendanceService.getAttendanceRecords(employeeId, month, page, size);
        return ApiResponse.success(records);
    }

    @PostMapping("/confirm")
    public ApiResponse<?> confirmAttendance(
            @RequestParam Long recordId,
            @RequestParam String status) {
        Long currentUserId = employeeService.getCurrentId();
        boolean success = attendanceService.confirmAttendance(recordId, status, currentUserId);
        return success ? ApiResponse.success() : ApiResponse.error(ErrorCode.SYSTEM_ERROR, "考勤确认失败");
    }
}
