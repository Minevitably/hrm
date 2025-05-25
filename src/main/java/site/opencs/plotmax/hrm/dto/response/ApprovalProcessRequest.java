package site.opencs.plotmax.hrm.dto.response;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class ApprovalProcessRequest {
    @NotNull(message = "审批ID不能为空")
    private Long approvalId;

    @NotNull(message = "审批结果不能为空")
    private Boolean approved;

    @Size(max = 500, message = "审批意见长度不能超过500字符")
    private String comment;
}
