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
 * 审批记录表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_approval")
@ApiModel(value = "Approval对象", description = "审批记录表")
public class Approval implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("申请人ID")
    private Long employeeId;

    @ApiModelProperty("类型(LEAVE/OVERTIME)")
    private String type;

    @ApiModelProperty("开始时间")
    private LocalDateTime startTime;

    @ApiModelProperty("结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty("时长(小时)")
    private BigDecimal duration;

    @ApiModelProperty("事由")
    private String reason;

    @ApiModelProperty("状态(PENDING/APPROVED/REJECTED)")
    private String status;

    @ApiModelProperty("审批人ID")
    private Long approverId;

    @ApiModelProperty("审批时间")
    private LocalDateTime approveTime;

    @ApiModelProperty("审批意见")
    private String approveComment;

    private LocalDateTime createTime;
}
