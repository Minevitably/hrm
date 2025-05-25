package site.opencs.plotmax.hrm.dto.response;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeResponse {
    private Long id;
    private String name;
    private String gender;
    private String birthDate;
    private String idCardNumber;
    private Long departmentId;
    private String departmentName;
    private Long positionId;
    private String positionName;
    private String hireDate;
    private BigDecimal baseSalary;
    private BigDecimal performanceCoefficient;
    private String education;
    private String contactPhone;
    private String emergencyContact;
    private String emergencyPhone;
    private String contractFileUrl;
    private String idCardFileUrl;
    private Integer status;
    private String createTime;
}
