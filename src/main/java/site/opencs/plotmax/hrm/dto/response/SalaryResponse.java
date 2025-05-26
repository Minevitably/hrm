package site.opencs.plotmax.hrm.dto.response;

import lombok.Data;

@Data
public class SalaryResponse {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private String yearMonth;

    // 工资明细
    private String baseSalary;
    private String performanceSalary;
    private String fullAttendanceBonus;
    private String otherBonus;

    // 扣款明细
    private String attendanceDeduction;
    private String insuranceDeduction;
    private String taxDeduction;
    private String otherDeduction;

    // 合计
    private String totalIncome;
    private String totalDeduction;
    private String netSalary;

    // 签收状态
    private Boolean isSigned;
    private String signTime;

    private String createTime;
}
