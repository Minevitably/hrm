package site.opencs.plotmax.hrm.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import site.opencs.plotmax.hrm.dto.response.AttendanceResponse;
import site.opencs.plotmax.hrm.entity.Attendance;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 考勤记录表 服务类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface IAttendanceService extends IService<Attendance> {

    /**
     * 处理打卡
     */
    boolean processCheckIn(Long employeeId, boolean isCheckIn);

    /**
     * 获取考勤记录
     */
    Page<AttendanceResponse> getAttendanceRecords(Long employeeId, String month, int page, int size);

    /**
     * 确认考勤状态
     */
    boolean confirmAttendance(Long recordId, String status, Long confirmedBy);

    /**
     * 自动计算考勤异常状态
     */
    void calculateAttendanceStatus(Attendance attendance);
}
