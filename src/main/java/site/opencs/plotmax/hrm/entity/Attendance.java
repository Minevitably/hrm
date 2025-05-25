package site.opencs.plotmax.hrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 考勤记录表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_attendance")
@ApiModel(value = "Attendance对象", description = "考勤记录表")
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("员工ID")
    private Long employeeId;

    @ApiModelProperty("考勤日期")
    private LocalDate attendanceDate;

    @ApiModelProperty("上班打卡时间")
    private LocalTime checkInTime;

    @ApiModelProperty("下班打卡时间")
    private LocalTime checkOutTime;

    @ApiModelProperty("打卡IP")
    private String checkInIp;

    @ApiModelProperty("迟到分钟数")
    private Integer lateMinutes;

    @ApiModelProperty("早退分钟数")
    private Integer earlyLeaveMinutes;

    @ApiModelProperty("是否旷工(1:是 0:否)")
    private Byte absence;

    @ApiModelProperty("状态(PENDING/CONFIRMED/REJECTED)")
    private String status;

    @ApiModelProperty("确认人ID")
    private Long confirmedBy;

    @ApiModelProperty("确认时间")
    private LocalDateTime confirmedTime;

    @ApiModelProperty("备注")
    private String remark;

    private LocalDateTime createTime;
}
