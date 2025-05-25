package site.opencs.plotmax.hrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "User对象", description = "系统用户表")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("关联员工ID")
    private Long employeeId;

    @ApiModelProperty("最后登录时间")
    private LocalDateTime lastLoginTime;

    @ApiModelProperty("连续登录失败次数")
    private Integer loginFailCount;

    @ApiModelProperty("是否锁定(1:是 0:否)")
    private Byte accountLocked;

    @ApiModelProperty("锁定截止时间")
    private LocalDateTime lockedUntil;

    @ApiModelProperty("状态(1:启用 0:禁用)")
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
    @TableField(exist = false) // 表示不是数据库字段
    private List<String> roles;
}
