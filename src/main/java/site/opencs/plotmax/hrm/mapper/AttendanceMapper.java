package site.opencs.plotmax.hrm.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import site.opencs.plotmax.hrm.dto.response.AttendanceResponse;
import site.opencs.plotmax.hrm.entity.Attendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 考勤记录表 Mapper 接口
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
public interface AttendanceMapper extends BaseMapper<Attendance> {

    @Select("<script>" +
            "SELECT a.*, e.name as employee_name, u.name as confirmed_by_name " +
            "FROM sys_attendance a " +
            "LEFT JOIN sys_employee e ON a.employee_id = e.id " +
            "LEFT JOIN sys_employee u ON a.confirmed_by = u.id " +
            "WHERE 1=1 " +
            "<if test='employeeId != null'> AND a.employee_id = #{employeeId} </if>" +
            "<if test='month != null'> AND DATE_FORMAT(a.attendance_date, '%Y-%m') = #{month} </if>" +
            "ORDER BY a.attendance_date DESC " +
            "</script>")
    Page<AttendanceResponse> selectAttendancePage(Page<AttendanceResponse> page,
                                                  @Param("employeeId") Long employeeId,
                                                  @Param("month") String month);
}
