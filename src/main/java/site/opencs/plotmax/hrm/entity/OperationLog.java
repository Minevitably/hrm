package site.opencs.plotmax.hrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 操作日志表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_operation_log")
@ApiModel(value = "OperationLog对象", description = "操作日志表")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("操作用户ID")
    private Long userId;

    @ApiModelProperty("操作类型")
    private String operation;

    @ApiModelProperty("请求方法")
    private String method;

    @ApiModelProperty("请求参数")
    private String params;

    @ApiModelProperty("IP地址")
    private String ip;

    @ApiModelProperty("操作状态(1:成功 0:失败)")
    private Byte status;

    @ApiModelProperty("错误消息")
    private String errorMsg;

    @ApiModelProperty("操作时间")
    private LocalDateTime operationTime;
}
