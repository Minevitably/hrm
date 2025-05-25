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
 * 职位表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_position")
@ApiModel(value = "Position对象", description = "职位表")
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("职位名称")
    private String name;

    @ApiModelProperty("职级")
    private Integer level;

    @ApiModelProperty("最低基本工资")
    private BigDecimal baseSalaryMin;

    @ApiModelProperty("最高基本工资")
    private BigDecimal baseSalaryMax;

    private LocalDateTime createTime;
}
