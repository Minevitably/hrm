package site.opencs.plotmax.hrm.dto.response;

import lombok.Data;

@Data
public class AttendanceResponse {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String attendanceDate;
    private String checkInTime;
    private String checkOutTime;
    private Integer lateMinutes;
    private Integer earlyLeaveMinutes;
    private Integer absence;
    private String status;
    private Long confirmedBy;
    private String confirmedByName;
    private String confirmedTime;
    private String remark;
}
