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
 * 部门表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_department")
@ApiModel(value = "Department对象", description = "部门表")
public class Department implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("部门名称")
    private String name;

    @ApiModelProperty("父部门ID")
    private Long parentId;

    @ApiModelProperty("部门经理ID")
    private Long managerId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
