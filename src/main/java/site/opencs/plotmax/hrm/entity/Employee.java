package site.opencs.plotmax.hrm.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author plotmax
 * @since 2025-05-25
 */
@Getter
@Setter
@TableName("sys_employee")
@ApiModel(value = "Employee对象", description = "员工表")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("性别")
    private String gender;

    @ApiModelProperty("出生日期")
    private LocalDate birthDate;

    @ApiModelProperty("身份证号")
    private String idCardNumber;

    @ApiModelProperty("部门ID")
    private Long departmentId;

    @ApiModelProperty("职位ID")
    private Long positionId;

    @ApiModelProperty("入职日期")
    private LocalDate hireDate;

    @ApiModelProperty("离职日期")
    private LocalDate leaveDate;

    @ApiModelProperty("基本工资")
    private BigDecimal baseSalary;

    @ApiModelProperty("绩效系数")
    private BigDecimal performanceCoefficient;

    @ApiModelProperty("学历")
    private String education;

    @ApiModelProperty("联系电话")
    private String contactPhone;

    @ApiModelProperty("紧急联系人")
    private String emergencyContact;

    @ApiModelProperty("紧急联系电话")
    private String emergencyPhone;

    @ApiModelProperty("合同文件路径")
    private String contractFile;

    @ApiModelProperty("身份证件路径")
    private String idCardFile;

    @ApiModelProperty("状态(1:在职 0:离职)")
    private Byte status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
