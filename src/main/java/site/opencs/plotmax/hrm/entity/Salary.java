package site.opencs.plotmax.hrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 薪资记录表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_salary")
@ApiModel(value = "Salary对象", description = "薪资记录表")
public class Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("员工ID")
    private Long employeeId;

    @ApiModelProperty("年月(YYYY-MM)")
    private String yearMonth;

    @ApiModelProperty("基本工资")
    private BigDecimal baseSalary;

    @ApiModelProperty("绩效工资")
    private BigDecimal performanceSalary;

    @ApiModelProperty("全勤奖")
    private BigDecimal fullAttendanceBonus;

    @ApiModelProperty("考勤扣款")
    private BigDecimal attendanceDeduction;

    @ApiModelProperty("社保公积金扣款")
    private BigDecimal insuranceDeduction;

    @ApiModelProperty("个税扣款")
    private BigDecimal taxDeduction;

    @ApiModelProperty("实发工资")
    private BigDecimal netSalary;

    @ApiModelProperty("是否签收(1:是 0:否)")
    private Byte isSigned;

    @ApiModelProperty("签收时间")
    private LocalDateTime signTime;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
