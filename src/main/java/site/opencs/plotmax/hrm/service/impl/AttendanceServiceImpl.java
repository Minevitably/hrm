package site.opencs.plotmax.hrm.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import site.opencs.plotmax.hrm.dto.response.AttendanceResponse;
import site.opencs.plotmax.hrm.entity.Attendance;
import site.opencs.plotmax.hrm.exception.BusinessException;
import site.opencs.plotmax.hrm.mapper.AttendanceMapper;
import site.opencs.plotmax.hrm.service.IAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * <p>
 * 考勤记录表 服务实现类
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl extends ServiceImpl<AttendanceMapper, Attendance> implements IAttendanceService {

    private final AttendanceMapper attendanceMapper;

    // 标准工作时间配置
    private static final LocalTime WORK_START_TIME = LocalTime.of(9, 0); // 上班时间9:00
    private static final LocalTime WORK_END_TIME = LocalTime.of(18, 0);  // 下班时间18:00

    @Override
    @Transactional
    public boolean processCheckIn(Long employeeId, boolean isCheckIn) {
        LocalDate today = LocalDate.now();

        LambdaUpdateWrapper<Attendance> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Attendance::getEmployeeId, employeeId)
                .eq(Attendance::getAttendanceDate, today);

        if (isCheckIn) {
            // 上班打卡
            Attendance attendance = this.getOne(wrapper);
            if (attendance == null) {
                attendance = new Attendance();
                attendance.setEmployeeId(employeeId);
                attendance.setAttendanceDate(today);
                attendance.setCheckInTime(LocalTime.now());
                attendance.setCreateTime(LocalDateTime.now());

                // 计算迟到分钟数
                long lateMinutes = ChronoUnit.MINUTES.between(WORK_START_TIME, attendance.getCheckInTime());
                attendance.setLateMinutes(lateMinutes > 0 ? (int) lateMinutes : 0);

                this.calculateAttendanceStatus(attendance);
                return this.save(attendance);
            } else {
                // 避免重复打卡
                return false;
            }
        } else {
            // 下班打卡
            Attendance attendance = this.getOne(wrapper);
            if (attendance != null && attendance.getCheckOutTime() == null) {
                attendance.setCheckOutTime(LocalTime.now());

                // 计算早退分钟数
                long earlyLeaveMinutes = ChronoUnit.MINUTES.between(attendance.getCheckOutTime(), WORK_END_TIME);
                attendance.setEarlyLeaveMinutes(earlyLeaveMinutes > 0 ? (int) earlyLeaveMinutes : 0);

                this.calculateAttendanceStatus(attendance);
                return this.updateById(attendance);
            }
            return false;
        }
    }

    @Override
    public Page<AttendanceResponse> getAttendanceRecords(Long employeeId, String month, int page, int size) {
        Page<AttendanceResponse> pageParam = new Page<>(page, size);
        return attendanceMapper.selectAttendancePage(pageParam, employeeId, month);
    }

    @Override
    @Transactional
    public boolean confirmAttendance(Long recordId, String status, Long confirmedBy) {
        Attendance attendance = this.getById(recordId);
        if (attendance == null) {
            throw new BusinessException("考勤记录不存在");
        }

        attendance.setStatus(status);
        attendance.setConfirmedBy(confirmedBy);
        attendance.setConfirmedTime(LocalDateTime.now());

        return this.updateById(attendance);
    }

    @Override
    public void calculateAttendanceStatus(Attendance attendance) {
        if (attendance.getCheckInTime() == null && attendance.getCheckOutTime() == null) {
            attendance.setAbsence((byte) 1);
            attendance.setStatus("ABSENCE");
        } else {
            attendance.setAbsence((byte) 0);

            if (attendance.getLateMinutes() != null && attendance.getLateMinutes() > 0) {
                attendance.setStatus("LATE");
            } else if (attendance.getEarlyLeaveMinutes() != null && attendance.getEarlyLeaveMinutes() > 0) {
                attendance.setStatus("EARLY_LEAVE");
            } else {
                attendance.setStatus("NORMAL");
            }
        }
    }
}
