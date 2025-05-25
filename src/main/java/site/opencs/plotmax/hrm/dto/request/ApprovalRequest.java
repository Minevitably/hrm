package site.opencs.plotmax.hrm.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
public class ApprovalRequest {
    @NotBlank(message = "申请类型不能为空")
    private String type; // LEAVE/OVERTIME

    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    @NotBlank(message = "申请事由不能为空")
    @Size(max = 500, message = "事由长度不能超过500字符")
    private String reason;
}
