package site.opencs.plotmax.hrm.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EmployeeRequest {
    private String name;
    private String gender;
    private String birthDate;
    private String idCardNumber;
    private Long departmentId;
    private Long positionId;
    private String hireDate;
    private BigDecimal baseSalary;
    private String education;
    private String contactPhone;
    private String emergencyContact;
    private String emergencyPhone;
}
