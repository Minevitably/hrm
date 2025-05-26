package site.opencs.plotmax.hrm.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
public class SalaryCalculateRequest {
    @NotNull(message = "员工ID不能为空")
    private Long employeeId;

    @NotBlank(message = "年月不能为空")
    @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "年月格式应为YYYY-MM")
    private String yearMonth;

    private Boolean recalculate = false; // 是否重新计算
}
